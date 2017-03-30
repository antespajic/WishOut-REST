package hr.asc.appic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import hr.asc.appic.controller.model.OfferExportModel;
import hr.asc.appic.controller.model.WishExportModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.exception.ContentCheck;
import hr.asc.appic.mapping.OfferMapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.mapping.WishMapper;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;

@Service
public class WishService {

    private static final String UPVOTE_COUNT_COLUMN = "upvoteCount";

    @Autowired
    private ListeningExecutorService listeningExecutorService;
    @Autowired
    private RepoProvider repoProvider;
    @Autowired
    private WishMapper wishMapper;
    @Autowired
    private OfferMapper offerMapper;
    
    @Autowired private UserMapper userMapper;
    

    public DeferredResult<ResponseEntity> getWish(Integer index, Integer size, String id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishExportModel> getWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = repoProvider.wishRepository.findById(id).get();
                    return exportWish(index, size, wish);
                }
        );

        Futures.addCallback(getWishJob, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> createWish(WishModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> createWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishMapper.modelToPojo(model);

                    User user = repoProvider.userRepository.findById(model.getUserId()).get();
                    ContentCheck.requireNonNull(model.getUserId(), user);

                    wish.setUser(user);
                    user.getWishes().add(wish);

                    if (model.getOfferId() != null) {
                        Offer offer = repoProvider.offerRepository.findById(model.getOfferId()).get();
                        ContentCheck.requireNonNull(model.getOfferId(), offer);

                        wish.setOffer(offer);
                    }

                    repoProvider.userRepository.save(user);
                    repoProvider.wishRepository.save(wish);
                    return wishMapper.pojoToModel(wish);
                }
        );

        Futures.addCallback(createWishJob, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> updateWish(String id, WishModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> updateWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = repoProvider.wishRepository.findById(id).get();
                    ContentCheck.requireNonNull(id, wish);
                    wishMapper.updatePojoFromModel(wish, model);

                    if (model.getOfferId() != null) {
                        Offer offer = repoProvider.offerRepository.findById(model.getOfferId()).get();
                        wish.setOffer(offer);
                    }

                    repoProvider.wishRepository.save(wish);
                    return null;
                }
        );

        Futures.addCallback(updateWishJob, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> deleteWish(String id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> deleteWishJob = listeningExecutorService.submit(
                () -> {
                    repoProvider.wishRepository.delete(id).get();
                    return null;
                }
        );

        Futures.addCallback(deleteWishJob, new ResponseEntityCallback<>(result));
        return result;
    }

    private WishExportModel exportWish(Integer index, Integer size, Wish wish) throws Exception {
        Pageable page = new PageRequest(index, size, new Sort(Sort.Direction.DESC, UPVOTE_COUNT_COLUMN));

        List<Offer> offers = repoProvider.offerRepository.findByWishId(wish.getId(), page);
        User creator = repoProvider.userRepository.findById(wish.getUser().getId()).get();

        WishExportModel wishExportModel = new WishExportModel();
        wishExportModel.setCreator(userMapper.lightModelFromUser(creator));
        wishExportModel.setWish(wishMapper.pojoToModel(wish));
        wishExportModel.setInteraction(userMapper.interactionModelForUser(creator, wish.getId()));

        for (Offer offer : offers) {
            User user = repoProvider.userRepository.findById(offer.getUserId()).get();

            OfferExportModel offerExportModel = new OfferExportModel();
            offerExportModel.setOffer(offerMapper.pojoToModel(offer));
            offerExportModel.setUser(userMapper.lightModelFromUser(user));
            offerExportModel.setInteraction(userMapper.interactionModelForUser(user, offer.getId()));

            wishExportModel.getOffers().add(offerExportModel);
        }

        return wishExportModel;
    }

}
