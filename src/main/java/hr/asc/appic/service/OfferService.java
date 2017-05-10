package hr.asc.appic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.mapping.OfferMapper;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.OfferRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OfferService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private OfferMapper offerMapper;

    public DeferredResult<ResponseEntity<OfferModel>> createOffer(OfferModel model) {
        DeferredResult<ResponseEntity<OfferModel>> result = new DeferredResult<>();

        ListenableFuture<OfferModel> createOfferJob = listeningExecutorService.submit(
                () -> {
                    Offer offer = offerMapper.modelToPojo(model);

                    User user = userRepository.findById(model.getUserId()).get();
                    Assert.notNull(user, "Could not find user with id: " + model.getUserId());

                    Wish wish = wishRepository.findById(model.getWishId()).get();
                    Assert.notNull(wish, "Could not find wish with id: " + model.getWishId());

                    user.getOffers().add(offer);
                    wish.getOffers().add(offer);

                    offerRepository.save(offer);
                    userRepository.save(user);
                    wishRepository.save(wish);

                    return offerMapper.pojoToModel(offer);
                }
        );

        Futures.addCallback(createOfferJob, new FutureCallback<OfferModel>() {

            @Override
            public void onSuccess(OfferModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while creating offer object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity> updateOffer(String id, OfferModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> updateOfferJob = listeningExecutorService.submit(
                () -> {
                    Offer offer = offerRepository.findById(id).get();
                    Assert.notNull(offer, "Could not find offer with id: " + id);

                    offerMapper.updatePojoFromModel(offer, model);
                    offerRepository.save(offer);

                    return null;
                }
        );

        Futures.addCallback(updateOfferJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while updating offer object", t);
            }
        });

        return result;
    }
}
