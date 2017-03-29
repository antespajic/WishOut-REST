package hr.asc.appic.service;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.exception.ContentCheck;
import hr.asc.appic.mapping.WishMapper;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@Service
public class WishService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;
    @Autowired
    private RepoProvider repoProvider;
    @Autowired
    private WishMapper mapper;

    public DeferredResult<ResponseEntity> getWish(BigInteger id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> getWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = repoProvider.wishRepository.findById(id).get();

                    return null;
                }
        );

        Futures.addCallback(getWishJob, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> createWish(WishModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> createWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = mapper.modelToPojo(model);

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
                    return mapper.pojoToModel(wish);
                }
        );

        Futures.addCallback(createWishJob, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> updateWish(WishModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> updateWishJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = repoProvider.wishRepository.findById(model.getId()).get();
                    mapper.updatePojoFromModel(wish, model);

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

    public DeferredResult<ResponseEntity> deleteWish(BigInteger id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> deleteWishJob = listeningExecutorService.submit(
                () -> {
                    repoProvider.wishRepository.delete(id).get();
                    return null;
                }
        );

        Futures.addCallback(deleteWishJob, new ResponseEntityCallback<>(result));
        return result;
    }
}
