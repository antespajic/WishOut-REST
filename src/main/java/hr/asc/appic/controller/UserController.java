package hr.asc.appic.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.controller.model.StoryViewModel;
import hr.asc.appic.controller.model.UserModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.service.UserService;

@RestController
@RequestMapping(value="/user")
public class UserController {

	@Autowired private UserService userService;
	
	@RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<UserModel>> registerUser(@RequestBody UserModel viewModel) {
		return userService.create(viewModel);
	}
	
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<UserModel>> getUser(@PathVariable String id) {
		return userService.get(id);
	}
	
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
	public DeferredResult<ResponseEntity<?>> updateUser(@PathVariable String id, @RequestBody UserModel viewModel) {
		return userService.update(id, viewModel);
	}
	
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.DELETE
    )
    public DeferredResult<ResponseEntity<?>> deleteUser(@PathVariable String id) {
		return userService.delete(id);
	}
	
	@RequestMapping(
			value = "/{id}/wishes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<Collection<WishModel>>> getUserWishes(@PathVariable String id) {
		return userService.getWishes(id);
	}
	
	@RequestMapping(
			value = "/{id}/stories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<Collection<StoryViewModel>>> getUserStories(@PathVariable String id) {
		return userService.getStories(id);
	}
	
	@RequestMapping(
			value = "/{id}/offers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<Collection<OfferModel>>> getUserOffers(@PathVariable String id) {
		return userService.getOffers(id);
	}
	
}
