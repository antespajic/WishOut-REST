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
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserMapper map;
    @Autowired
    private WishMapper wishMapper;
    @Autowired
    private StoryMapper storyMapper;
    @Autowired
    private OfferMapper offerMapper;

    public DeferredResult<ResponseEntity<UserModel>> createUser(UserModel model) {
        DeferredResult<ResponseEntity<UserModel>> result = new DeferredResult<>();

        userRepository.save(map.modelToPojo(model)).addCallback(
                response -> result.setResult(ResponseEntity.status(HttpStatus.OK).body(map.pojoToModel(response))),
                error -> {
                    result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                    log.error("Error while creating user", error);
                });
        return result;
    }

    public DeferredResult<ResponseEntity<UserModel>> getUser(String id) {
        DeferredResult<ResponseEntity<UserModel>> result = new DeferredResult<>();

        userRepository.findById(id).addCallback(
                response -> {
                    Assert.notNull(response, "Couldn't find a user with provided ID");
                    result.setResult(ResponseEntity.ok(map.pojoToModel(response)));
                },
                e -> {
                    result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                    log.error("Error while getting user", e);
                });
        return result;
    }

    public DeferredResult<ResponseEntity<?>> updateUser(String id, UserModel model) {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        ListenableFuture<Void> getUser = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Couldn't find user with ID " + id);
                    updateUserPartial(user, model);
                    userRepository.save(user);
                    return null;
                }
        );

        Futures.addCallback(getUser, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error while updating user", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<?>> deleteUser(String id) {
        DeferredResult<ResponseEntity<?>> result = new DeferredResult<>();

        userRepository.delete(id).addCallback(
                response -> result.setResult(ResponseEntity.ok().build()),
                error -> {
                    result.setResult(ResponseEntity.badRequest().build());
                    log.error("Error during deleting user", error);
                });
        return result;
    }

    public DeferredResult<ResponseEntity<Collection<WishModel>>> getWishes(String id) {
        DeferredResult<ResponseEntity<Collection<WishModel>>> result = new DeferredResult<>();

        ListenableFuture<Collection<WishModel>> getWishesJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Couldn't find user with ID " + id);
                    return user.getWishes().stream()
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
                log.error("Error while retrieving user wishes", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<Collection<StoryModel>>> getStories(String id) {
        DeferredResult<ResponseEntity<Collection<StoryModel>>> result = new DeferredResult<>();

        ListenableFuture<Collection<StoryModel>> getStoriesJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Couldn't find user with ID " + id);
                    return user.getStories().stream()
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
                log.error("Error while retrieving user stories", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<Collection<OfferModel>>> getOffers(String id) {
        DeferredResult<ResponseEntity<Collection<OfferModel>>> result = new DeferredResult<>();

        ListenableFuture<Collection<OfferModel>> getOffersJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Couldn't find user with ID " + id);
                    return user.getOffers().stream()
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
                log.error("Error while retrieving user offers", t);
            }
        });

        return result;
    }

    private void updateUserPartial(User user, UserModel viewModel) {
        if (viewModel.getFirstName() != null) {
            user.setFirstName(viewModel.getFirstName());
        }
        if (viewModel.getLastName() != null) {
            user.setLastName(viewModel.getLastName());
        }
        if (viewModel.getCountry() != null) {
            user.setCountry(viewModel.getCountry());
        }
        if (viewModel.getCity() != null) {
            user.setCity(viewModel.getCity());
        }
        if (viewModel.getGender() != null) {
            user.setGender(viewModel.getGender());
        }
        if (viewModel.getDateOfBirth() != null) {
            user.setDateOfBirth(viewModel.getDateOfBirth());
        }
        if (viewModel.getContactNumber() != null) {
            user.setContactNumber(viewModel.getContactNumber());
        }
        if (viewModel.getContactFacebook() != null) {
            user.setContactFacebook(viewModel.getContactFacebook());
        }
        if (viewModel.getProfileConfirmed() != null) {
            user.setProfileConfirmed(viewModel.getProfileConfirmed());
        }
        if (viewModel.getCoins() != null) {
            user.setCoins(viewModel.getCoins());
        }
    }
}
