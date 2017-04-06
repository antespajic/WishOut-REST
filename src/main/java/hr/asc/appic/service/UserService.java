package hr.asc.appic.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.controller.model.StoryModel;
import hr.asc.appic.controller.model.UserModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.mapping.OfferMapper;
import hr.asc.appic.mapping.StoryMapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.mapping.WishMapper;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private WishMapper wishMapper;
    @Autowired
    private StoryMapper storyMapper;
    @Autowired
    private OfferMapper offerMapper;

    public DeferredResult<ResponseEntity<UserModel>> createUser(UserModel model) {
        DeferredResult<ResponseEntity<UserModel>> result = new DeferredResult<>();

        ListenableFuture<UserModel> createUserJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.save(userMapper.modelToPojo(model)).get();
                    return userMapper.pojoToModel(user);
                }
        );

        Futures.addCallback(createUserJob, new FutureCallback<UserModel>() {

            @Override
            public void onSuccess(UserModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while creating user object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<UserModel>> getUser(String id) {
        DeferredResult<ResponseEntity<UserModel>> result = new DeferredResult<>();

        ListenableFuture<UserModel> getUserJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    return userMapper.pojoToModel(user);
                }
        );

        Futures.addCallback(getUserJob, new FutureCallback<UserModel>() {

            @Override
            public void onSuccess(UserModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while retrieving user object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity> updateUser(String id, UserModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> updateUserJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    userMapper.updateUserFromModel(user, model);
                    userRepository.save(user);
                    return null;
                }
        );

        Futures.addCallback(updateUserJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while updating user object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<Collection<WishModel>>> getWishes(String id) {
        DeferredResult<ResponseEntity<Collection<WishModel>>> result = new DeferredResult<>();

        ListenableFuture<Collection<WishModel>> getWishesJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    return user.getWishes().stream()
                            .sorted(Comparator.reverseOrder())
                            .map(wishMapper::pojoToModel)
                            .collect(Collectors.toList());
                }
        );

        Futures.addCallback(getWishesJob, new FutureCallback<Collection<WishModel>>() {

            @Override
            public void onSuccess(Collection<WishModel> collection) {
                result.setResult(ResponseEntity.ok(collection));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while retrieving user's wishes", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<Collection<StoryModel>>> getStories(String id) {
        DeferredResult<ResponseEntity<Collection<StoryModel>>> result = new DeferredResult<>();

        ListenableFuture<Collection<StoryModel>> getStoriesJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    return user.getStories().stream()
                            .sorted(Comparator.reverseOrder())
                            .map(storyMapper::pojoToModel)
                            .collect(Collectors.toList());
                }
        );

        Futures.addCallback(getStoriesJob, new FutureCallback<Collection<StoryModel>>() {

            @Override
            public void onSuccess(Collection<StoryModel> collection) {
                result.setResult(ResponseEntity.ok(collection));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while retrieving user's stories", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<Collection<OfferModel>>> getOffers(String id) {
        DeferredResult<ResponseEntity<Collection<OfferModel>>> result = new DeferredResult<>();

        ListenableFuture<Collection<OfferModel>> getOffersJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    return user.getOffers().stream()
                            .sorted(Comparator.reverseOrder())
                            .map(offerMapper::pojoToModel)
                            .collect(Collectors.toList());
                }
        );

        Futures.addCallback(getOffersJob, new FutureCallback<Collection<OfferModel>>() {

            @Override
            public void onSuccess(Collection<OfferModel> collection) {
                result.setResult(ResponseEntity.ok(collection));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while retrieving user's offers", t);
            }
        });

        return result;
    }
}
