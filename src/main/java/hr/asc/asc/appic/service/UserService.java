package hr.asc.asc.appic.service;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.mapping.Mapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.asc.appic.controller.model.UserViewModel;
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
				error -> log.error("Error", error));
		return res;
	}

	public DeferredResult<ResponseEntity<UserViewModel>> get(Long id) {
		DeferredResult<ResponseEntity<UserViewModel>> res = new DeferredResult<>();
		userRepository.findById(BigInteger.valueOf(id)).addCallback(
				response -> res.setResult(ResponseEntity.status(HttpStatus.OK).body(map.pojoToModel(response))),
				e -> log.error("Error", e));
		return res;
	}

	public DeferredResult<ResponseEntity<?>> update(Long id, UserViewModel viewModel) {
		DeferredResult<ResponseEntity<?>> res = new DeferredResult<>();

		try {
			User user = userRepository.findById(BigInteger.valueOf(id)).get();
			// TODO: hoping that we get all attributes??

			userRepository.save(user).addCallback(
					response -> res.setResult(ResponseEntity.ok().build()), 
					error -> {
						res.setResult(ResponseEntity.noContent().build());
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
