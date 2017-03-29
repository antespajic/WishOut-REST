package hr.asc.appic.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListeningExecutorService;

import hr.asc.appic.controller.model.UserViewModel;
import hr.asc.appic.mapping.Mapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ListeningExecutorService listeningExecutorService;
	
	private Mapper<User, UserViewModel> map = new UserMapper();

	public DeferredResult<ResponseEntity<UserViewModel>> create(UserViewModel viewModel) {
		DeferredResult<ResponseEntity<UserViewModel>> res = new DeferredResult<>();

		userRepository.save(map.modelToPojo(viewModel)).addCallback(
				response -> res.setResult(ResponseEntity.status(HttpStatus.OK).body(map.pojoToModel(response))),
				error -> {
					res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
					log.error("Error while creating user", error);	
				});
		return res;
	}

	public DeferredResult<ResponseEntity<UserViewModel>> get(Long id) {
		DeferredResult<ResponseEntity<UserViewModel>> res = new DeferredResult<>();
		userRepository.findById(BigInteger.valueOf(id)).addCallback(
				response -> {
					Assert.notNull(response, "Couldn't find a user with provided ID");
					res.setResult(ResponseEntity.status(HttpStatus.OK).body(map.pojoToModel(response)));
				},
				e -> {
					res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
					log.error("Error while getting user", e);	
				});
		return res;
	}

	public DeferredResult<ResponseEntity<?>> update(Long id, UserViewModel viewModel) {
		DeferredResult<ResponseEntity<?>> res = new DeferredResult<>();

		com.google.common.util.concurrent.ListenableFuture<ResponseEntity<?>>
		getUser = listeningExecutorService.submit(
				() -> {
					User user = userRepository.findById(BigInteger.valueOf(id)).get();
					Assert.notNull(user, "Couldn't find user with ID " + id);
					
					updateUserPartial(user, viewModel);
					
					userRepository.save(user);
					
					return ResponseEntity.ok().build();
				}
		);
		
		Futures.addCallback(getUser, new FutureCallback<ResponseEntity<?>>() {

			@Override
			public void onSuccess(ResponseEntity<?> result) {
				res.setResult(result);
			}

			@Override
			public void onFailure(Throwable t) {
				log.error("Error in updating user", t);
			}
		}); 

		return res;
	}

	public DeferredResult<ResponseEntity<?>> delete(Long id) {
		DeferredResult<ResponseEntity<?>> res = new DeferredResult<>();
		userRepository.delete(BigInteger.valueOf(id))
				.addCallback(
						response -> res.setResult(ResponseEntity.ok().build()),
						error -> {
							res.setResult(ResponseEntity.badRequest().build());
							log.error("Error during deleting user", error);
						});
		return res;
	}

	private void updateUserPartial(User user, UserViewModel viewModel) {
		if (viewModel.getName() != null) {
			user.setName(viewModel.getName());
		}
		
		if (viewModel.getSurname() != null) {
			user.setSurname(viewModel.getSurname());
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
		
		if (viewModel.getContactFacebook() != null) {
			user.setContactFacebook(viewModel.getContactFacebook());
		}
		
		if (viewModel.getContactNumber() != null) {
			user.setContactNumber(viewModel.getContactNumber());
		}
		
		if (viewModel.getProfilePicture() != null) {
			user.setProfilePicture(viewModel.getProfilePicture());
		}
		
		if (viewModel.getUpvotes() != null) {
			user.setUpvotes(viewModel.getUpvotes());
		}
		
		if (viewModel.getCoins() != null) {
			user.setCoins(viewModel.getCoins());
		}
		
		if (viewModel.getProfileConfirmed() != null) {
			user.setProfileConfirmed(viewModel.getProfileConfirmed());
		}
	}
}
