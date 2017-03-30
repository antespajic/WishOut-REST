package hr.asc.appic.mapping;

import java.math.BigInteger;

import org.springframework.stereotype.Service;

import hr.asc.appic.controller.model.InteractionModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.controller.model.UserViewModel;
import hr.asc.appic.persistence.model.User;

@Service
public class UserMapper implements Mapper<User, UserViewModel> {

	@Override
	public User modelToPojo(UserViewModel model) {
		return new User()
			.setEmail(model.getEmail())
			.setName(model.getName())
			.setSurname(model.getSurname())
			.setContactNumber(model.getContactNumber())
			.setContactFacebook(model.getContactFacebook())
			.setCountry(model.getCountry())
			.setCity(model.getCity())
			.setProfilePicture(model.getProfilePicture())
			.setDateOfBirth(model.getDateOfBirth())
			.setDateOfRegistration(model.getDateOfRegistration())
			.setProfileConfirmed(model.getProfileConfirmed())
			.setGender(model.getGender())
			.setCoins(model.getCoins())
			.setPassword(model.getPassword());
	}

	@Override
	public UserViewModel pojoToModel(User pojo) {
		return new UserViewModel()
			.setId(pojo.getId())
			.setEmail(pojo.getEmail())
			.setName(pojo.getName())
			.setSurname(pojo.getSurname())
			.setContactNumber(pojo.getContactNumber())
			.setContactFacebook(pojo.getContactFacebook())
			.setCountry(pojo.getCountry())
			.setCity(pojo.getCity())
			.setProfilePicture(pojo.getProfilePicture())
			.setDateOfBirth(pojo.getDateOfBirth())
			.setDateOfRegistration(pojo.getDateOfRegistration())
			.setProfileConfirmed(pojo.isProfileConfirmed())
			.setGender(pojo.getGender())
			.setCoins(pojo.getCoins())
			.setPassword(pojo.getPassword())
			.setUpvotes(pojo.getUpvotes())
			.setReports(pojo.getReports());
	}
	
	public UserLightViewModel lightModelFromUser(User user) {
		return new UserLightViewModel()
			.setId(user.getId())
        	.setFirstName(user.getName())
        	.setLastName(user.getSurname())
        	.setProfilePicture(user.getProfilePicture());
    }
	
	public InteractionModel interactionModelForUser(User user, String resource) {
        return new InteractionModel()
        		.setUpvoted(user.getUpvotes().contains(resource))
        		.setReported(user.getReports().contains(resource));
    }

}
