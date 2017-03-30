package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.UserViewModel;
import hr.asc.appic.persistence.model.User;

public class UserMapper implements Mapper<User, UserViewModel> {

	@Override
	public User modelToPojo(UserViewModel model) {
		User u = new User();
		u.setEmail(model.getEmail())
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
		return u;
	}

	@Override
	public UserViewModel pojoToModel(User pojo) {
		UserViewModel uvm = new UserViewModel();
		uvm.setEmail(pojo.getEmail())
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
			.setPassword(pojo.getPassword());
		return uvm;
	}

}
