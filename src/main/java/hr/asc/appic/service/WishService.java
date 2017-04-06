package hr.asc.appic.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.controller.model.WishExportModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.elasticsearch.repository.WishElasticRepository;
import hr.asc.appic.mapping.OfferMapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.mapping.WishMapper;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.OfferRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

@Slf4j
@Service
public class WishService {

    private static final String UPVOTE_COUNT_COLUMN = "upvoteCount";

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private WishElasticRepository wishElasticRepository;
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WishMapper wishMapper;
    @Autowired
    private OfferMapper offerMapper;

    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;

    public DeferredResult<ResponseEntity<WishModel>> createWish(WishModel model) {
        DeferredResult<ResponseEntity<WishModel>> result = new DeferredResult<>();
        ListenableFuture<WishModel> createWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishMapper.modelToPojo(model);
                    User user = userRepository.findById(model.getUserId()).get();
                    Assert.notNull(user, "Could not find user with id: " + model.getUserId());

                    wish.setUser(user);
                    wish = wishRepository.save(wish).get();

                    user.getWishes().add(wish);
                    user = userRepository.save(user).get();

                    wishElasticRepository.save(
                            wishMapper.toElasticModel(wish, userMapper.lightModelFromUser(user))
                    );
                    return wishMapper.pojoToModel(wish);
                }
        );

        Futures.addCallback(createWishJob, new FutureCallback<WishModel>() {

            @Override
            public void onSuccess(WishModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while creating wish object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<WishExportModel>> getWish(String id, Integer index, Integer size) {
        DeferredResult<ResponseEntity<WishExportModel>> result = new DeferredResult<>();

        ListenableFuture<WishExportModel> getWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishRepository.findById(id).get();
                    Assert.notNull(wish, "Could not find wish with id: " + id);
                    return exportWish(wish, index, size);
                }
        );

        Futures.addCallback(getWishJob, new FutureCallback<WishExportModel>() {

            @Override
            public void onSuccess(WishExportModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while retrieving wish object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<?>> updateWish(String id, WishModel model) {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        ListenableFuture<Void> updateWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishRepository.findById(id).get();
                    Assert.notNull(wish, "Could not find wish with id: " + id);

                    wishMapper.updatePojoFromModel(wish, model);
                    wish = wishRepository.save(wish).get();

                    wishElasticRepository.save(
                            wishMapper.toElasticModel(
                                    wish, userMapper.lightModelFromUser(wish.getUser())
                            )
                    );
                    return null;
                }
        );

        Futures.addCallback(updateWishJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while updating wish object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity> updateOffer(String wishId, String offerId, boolean confirmed) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> updateOfferForWishJob = listeningExecutorService.submit(
                () -> {
                    updateOfferForWish(wishId, offerId, confirmed);
                    return null;
                }
        );

        Futures.addCallback(updateOfferForWishJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while assigning offer to wish", t);
            }
        });

        return result;
    }

    private WishExportModel exportWish(Wish wish, Integer index, Integer size) throws Exception {
        User creator = userRepository.findById(wish.getUser().getId()).get();
        Assert.notNull(creator, "Could not find user with id: " + wish.getUser().getId());

        List<Offer> chosenOffers = offerRepository.findByWishIdAndChosen(wish.getId(), true);
        int chosenSize = chosenOffers.size();
        Assert.isTrue(chosenSize == 0 || chosenSize == 1,
                "Invalid number of accepted offers for wish.");

        Pageable page = new PageRequest(index, size, new Sort(Sort.Direction.DESC, UPVOTE_COUNT_COLUMN));
        List<Offer> allOffers = offerRepository.findByWishId(wish.getId(), page);

        WishExportModel wishExportModel = new WishExportModel();
        wishExportModel.setCreator(userMapper.lightModelFromUser(creator));
        wishExportModel.setWish(wishMapper.pojoToModel(wish));

        // Setting chosen offer, if such is present.
        if (chosenSize == 1) {
            Offer chosenOffer = chosenOffers.get(0);
            User chosenUser = userRepository.findById(chosenOffer.getUserId()).get();
            Assert.notNull(chosenUser, "Could not find chosen user with id: " + chosenOffer.getUserId());
            wishExportModel.setChosenOffer(offerMapper.exportModelForUser(chosenOffer, chosenUser));
            // In the future, interaction needs to be implemented.
        }

        // In the future, myOffer needs to be implemented.
        // In the future, interaction needs to be implemented.

        for (Offer offer : allOffers) {
            User user = userRepository.findById(offer.getUserId()).get();
            Assert.notNull(user, "Could not find offer user with id: " + offer.getUserId());

            wishExportModel.getOffers().add(offerMapper.exportModelForUser(offer, user));
            // In the future, interaction needs to be implemented.
        }

        return wishExportModel;
    }

    private void updateOfferForWish(String wishId, String offerId, boolean confirmed) throws Exception {
        Wish wish = wishRepository.findById(wishId).get();
        Assert.notNull(wish, "Could not find wish with id: " + wishId);

        Offer offer = offerRepository.findById(offerId).get();
        Assert.notNull(offer, "Could not find offer with id: " + offerId);

        if (!wish.getOffers().contains(offer)) {
            throw new IllegalArgumentException("Wish does not have specified offer in it's offer list.");
        }

        if (confirmed) {
            for (Offer wishOffer : wish.getOffers()) {
                if (wishOffer.getChosen()) {
                    throw new IllegalArgumentException("Wish already has offer set as chosen one.");
                }
            }
        }

        offer.setChosen(confirmed);
        offerRepository.save(offer);
    }
}
