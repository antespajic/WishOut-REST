package hr.asc.appic.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.OfferRepository;
import hr.asc.appic.persistence.repository.StoryRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
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
public class ReportService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private StoryRepository storyRepository;

    public DeferredResult<ResponseEntity> report(ContentOrigin origin,
                                                 String resourceId,
                                                 String userId) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> reportJob = listeningExecutorService.submit(
                () -> {
                    registerReport(userId, resourceId);
                    switch (origin) {
                        case WISH:
                            reportWish(resourceId);
                            break;
                        case OFFER:
                            reportOffer(resourceId);
                            break;
                        case STORY:
                            reportStory(resourceId);
                            break;
                    }
                    return null;
                }
        );


        Futures.addCallback(reportJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred during report action", t);
            }
        });

        return result;
    }

    @Transactional
    private void registerReport(String userId, String resourceId) throws Exception {
        User user = userRepository.findById(userId).get();
        Assert.notNull(user, "Report failed. Could not find user with id: " + userId);

        if (user.getReports().contains(resourceId)) {
            throw new IllegalArgumentException("Report failed. User already has specified resource reported.");
        }

        user.getReports().add(resourceId);
        userRepository.save(user);
    }

    @Transactional
    private void reportWish(String wishId) throws Exception {
        Wish wish = wishRepository.findById(wishId).get();
        Assert.notNull(wish, "Report failed. Wish for id could not be found: " + wishId);
        wish.setReportCount(wish.getReportCount() + 1);
        wishRepository.save(wish);
    }

    @Transactional
    private void reportOffer(String offerId) throws Exception {
        Offer offer = offerRepository.findById(offerId).get();
        Assert.notNull(offer, "Report failed. Offer for id could not be found: " + offerId);
        offer.setReportCount(offer.getReportCount() + 1);
        offerRepository.save(offer);
    }

    @Transactional
    private void reportStory(String storyId) throws Exception {
        Story story = storyRepository.findById(storyId).get();
        Assert.notNull(story, "Report failed. Story for id could not be found: " + storyId);
        story.setReportCount(story.getReportCount() + 1);
        storyRepository.save(story);
    }
}
