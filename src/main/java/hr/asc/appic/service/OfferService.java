package hr.asc.appic.service;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.exception.ContentCheck;
import hr.asc.appic.mapping.OfferMapper;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@Service
public class OfferService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;
    @Autowired
    private RepoProvider repoProvider;
    @Autowired
    private OfferMapper mapper;

    public DeferredResult<ResponseEntity> getOffer(BigInteger id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<OfferModel> getOffer = listeningExecutorService.submit(
                () -> {
                    Offer offer = repoProvider.offerRepository.findById(id).get();
                    ContentCheck.requireNonNull(id, offer);
                    return mapper.pojoToModel(offer);
                }
        );

        Futures.addCallback(getOffer, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> createOffer(OfferModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<OfferModel> createOfferJob = listeningExecutorService.submit(
                () -> {
                    Offer offer = mapper.modelToPojo(model);

                    User user = repoProvider.userRepository.findById(model.getUserId()).get();
                    ContentCheck.requireNonNull(model.getUserId(), user);

                    Wish wish = repoProvider.wishRepository.findById(model.getWishId()).get();
                    ContentCheck.requireNonNull(model.getWishId(), wish);

                    offer.setUserId(user.getId());
                    offer.setWishId(wish.getId());
                    user.getOffers().add(offer);
                    wish.getOffers().add(offer);

                    repoProvider.userRepository.save(user);
                    repoProvider.wishRepository.save(wish);
                    repoProvider.offerRepository.save(offer);

                    return mapper.pojoToModel(offer);
                }
        );

        Futures.addCallback(createOfferJob, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> deleteOffer(BigInteger id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> deleteOfferJob = listeningExecutorService.submit(
                () -> {
                    repoProvider.offerRepository.delete(id);
                    return null;
                }
        );

        Futures.addCallback(deleteOfferJob, new ResponseEntityCallback<>(result));
        return result;
    }
}
