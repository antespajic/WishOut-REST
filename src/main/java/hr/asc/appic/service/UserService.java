package hr.asc.appic.service;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.context.request.async.DeferredResult;

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

		try {
			ListenableFuture<User> getUserById = 
			//User user = userRepository.findById(BigInteger.valueOf(id)).get();
			//Assert.notNull(user, "Couldn't find a user with provided ID");
			// TODO: hoping that we get all attributes??

			userRepository.save(null).addCallback(
					response -> res.setResult(ResponseEntity.ok().build()), 
					error -> {
						res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
						log.error("Error during updating user", error);
					});
		} catch (InterruptedException e) {
			log.error("InterruptedException in updateUser ", e);
		} catch (ExecutionException e) {
			log.error("ExecutionException in updateUser ", e);
		}

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

}
