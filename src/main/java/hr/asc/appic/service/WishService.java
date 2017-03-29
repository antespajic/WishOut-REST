package hr.asc.appic.service;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.mapping.WishMapper;
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

    public DeferredResult<ResponseEntity> createWish(WishModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> createWish = listeningExecutorService.submit(
                () -> {
                    Wish wish = mapper.modelToPojo(model);
                    wish.setUser(repoProvider.userRepository.findById(model.getUserId()).get());

                    // TODO: Dodat useru wish

                    if (model.getOfferId() != null) {
                        wish.setOffer(repoProvider.offerRepository.findById(model.getOfferId()).get());
                    }

                    repoProvider.wishRepository.save(wish);
                    return mapper.pojoToModel(wish);
                }
        );

        Futures.addCallback(createWish, new ResponseEntityCallback<>(result));
        return result;
    }

    public DeferredResult<ResponseEntity> deleteWish(BigInteger id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<WishModel> deleteWish = listeningExecutorService.submit(
                () -> {
                    repoProvider.wishRepository.delete(id).get();
                    return null;
                }
        );

        Futures.addCallback(deleteWish, new ResponseEntityCallback<>(result));
        return result;
    }
}
