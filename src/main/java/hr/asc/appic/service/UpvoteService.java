package hr.asc.appic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;


@Service
public class UpvoteService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;
    @Autowired
    private RepoProvider repoProvider;

    public DeferredResult<ResponseEntity> upvote(ContentOrigin origin,
    		String resourceId,
                                                 String userId) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> upvoteJob = listeningExecutorService.submit(
                () -> {
                    User user = repoProvider.userRepository.findById(userId).get();
                    user.getUpvotes().add(resourceId);
                    repoProvider.userRepository.save(user);

                    switch (origin) {
                        case WISH:
                            Wish wish = repoProvider.wishRepository.findById(resourceId).get();
                            wish.setUpvoteCount(wish.getUpvoteCount() + 1);
                            repoProvider.wishRepository.save(wish);
                            break;
                        case OFFER:
                            Offer offer = repoProvider.offerRepository.findById(resourceId).get();
                            offer.setUpvoteCount(offer.getUpvoteCount() + 1);
                            repoProvider.offerRepository.save(offer);
                            break;
                    }

                    return null;
                }
        );

        Futures.addCallback(upvoteJob, new ResponseEntityCallback<>(result));
        return result;
    }
}
