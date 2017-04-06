package hr.asc.appic.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.OfferRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import hr.asc.appic.service.utility.ContentOrigin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@Service
public class UpvoteService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private OfferRepository offerRepository;

    public DeferredResult<ResponseEntity> upvote(ContentOrigin origin,
                                                 String resourceId,
                                                 String userId) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> upvoteJob = listeningExecutorService.submit(
                () -> {
                    registerUpvote(userId, resourceId);
                    switch (origin) {
                        case WISH:
                            upvoteWish(resourceId);
                            break;
                        case OFFER:
                            upvoteOffer(resourceId);
                            break;
                    }
                    return null;
                }
        );

        Futures.addCallback(upvoteJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred during upvote action", t);
            }
        });

        return result;
    }

    @Transactional
    private void registerUpvote(String userId, String resourceId) throws Exception {
        User user = userRepository.findById(userId).get();
        Assert.notNull(user, "Upvote failed. Could not find user with id: " + userId);

        if (user.getUpvotes().contains(resourceId)) {
            throw new IllegalArgumentException("Upvote failed. User already has specified resource upvoted.");
        }

        user.getUpvotes().add(resourceId);
        userRepository.save(user);
    }

    @Transactional
    private void upvoteWish(String wishId) throws Exception {
        Wish wish = wishRepository.findById(wishId).get();
        Assert.notNull(wish, "Upvote failed. Wish for id could not be found: " + wishId);
        wish.setUpvoteCount(wish.getUpvoteCount() + 1);
        wishRepository.save(wish);
    }

    @Transactional
    private void upvoteOffer(String offerId) throws Exception {
        Offer offer = offerRepository.findById(offerId).get();
        Assert.notNull(offer, "Upvote failed. Offer for id could not be found: " + offerId);
        offer.setUpvoteCount(offer.getUpvoteCount() + 1);
        offerRepository.save(offer);
    }
}
