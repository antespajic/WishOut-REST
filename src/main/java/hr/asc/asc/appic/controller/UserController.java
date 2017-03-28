package hr.asc.asc.appic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.asc.appic.controller.model.UserViewModel;
import hr.asc.asc.appic.service.UserService;

@RestController(value="/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<UserViewModel>> registerUser(@RequestBody UserViewModel viewModel) {
		return userService.create(viewModel);
	}
	
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<UserViewModel>> getUser(@PathVariable Long id) {
		return userService.get(id);
	}
	
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
	public DeferredResult<ResponseEntity<?>> updateUser(@PathVariable Long id, @RequestBody UserViewModel viewModel) {
		return userService.update(id, viewModel);
	}
	
	@RequestMapping(
			value = "/{id}",
            method = RequestMethod.DELETE
    )
    public DeferredResult<ResponseEntity<?>> deleteUser(@PathVariable Long id) {
		return userService.delete(id);
	}
	
	
	
}
