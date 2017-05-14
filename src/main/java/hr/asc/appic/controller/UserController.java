package hr.asc.appic.controller;

import java.util.Collection;

import hr.asc.appic.controller.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.service.UserService;
import hr.asc.appic.service.WishService;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired private WishService wishService;

    @RequestMapping(
    		value = "/all",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE
	)
    public DeferredResult<ResponseEntity<Collection<UserModel>>> getAllUsers() {
    	return userService.getAllUsers();
    }
    
    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<UserModel>> registerUser(@RequestBody UserModel viewModel) {
        return userService.createUser(viewModel);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<UserModel>> getUser(@PathVariable String id) {
        return userService.getUserById(id);
    }
    
    @RequestMapping(
    		value = "/email/{email:.+}",
    		method = RequestMethod.GET,
    		produces = MediaType.APPLICATION_JSON_VALUE
	)
    public DeferredResult<ResponseEntity<UserModel>> getUserByEmail(@PathVariable String email) {
    	return userService.getUserByEmail(email);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<?>> updateUser(@PathVariable String id,
                                                        @RequestBody UserModel viewModel) {
        return userService.updateUser(id, viewModel);
    }

    @RequestMapping(
            value = "/{id}/wishes",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<Collection<WishExportModel>>> getUserWishes(@PathVariable String id) {
        return userService.getWishes(id);
    }

    @RequestMapping(
            value = "/{id}/stories",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<Collection<StoryExportModel>>> getUserStories(@PathVariable String id) {
        return userService.getStories(id);
    }

    @RequestMapping(
            value = "/{id}/offers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<Collection<WishModel>>> getUserOffers(@PathVariable String id) {
    	// Returns wishes that user made an offer to
    	return userService.getOffers(id);
    }
}
