package hr.asc.appic.service;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@Service
public class ReportService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;
    @Autowired
    private RepoProvider repoProvider;

    public DeferredResult<ResponseEntity> report(ContentOrigin origin,
                                                 BigInteger resourceId,
                                                 BigInteger userId) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> reportJob = listeningExecutorService.submit(
                () -> {
                    User user = repoProvider.userRepository.findById(userId).get();
                    user.getReports().add(resourceId);
                    repoProvider.userRepository.save(user);

                    switch (origin) {
                        case WISH:
                            Wish wish = repoProvider.wishRepository.findById(resourceId).get();
                            wish.setReportCount(wish.getReportCount() + 1);
                            repoProvider.wishRepository.save(wish);
                            break;
                        case OFFER:
                            Offer offer = repoProvider.offerRepository.findById(resourceId).get();
                            offer.setReportCount(offer.getReportCount() + 1);
                            repoProvider.offerRepository.save(offer);
                            break;
                        case STORY:
                            Story story = repoProvider.storyRepository.findById(resourceId).get();
                            story.setReportCount(story.getReportCount() + 1);
                            repoProvider.storyRepository.save(story);
                            break;
                    }

                    return null;
                }
        );

        Futures.addCallback(reportJob, new ResponseEntityCallback<>(result));
        return result;
    }
}
