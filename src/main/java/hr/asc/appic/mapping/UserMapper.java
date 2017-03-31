package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.InteractionModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.controller.model.UserModel;
import hr.asc.appic.persistence.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Mapper<User, UserModel> {

    @Override
    public User modelToPojo(UserModel model) {
        return new User()
                .setEmail(model.getEmail())
                .setFirstName(model.getFirstName())
                .setLastName(model.getLastName())
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
    public UserModel pojoToModel(User pojo) {
        return new UserModel()
                .setId(pojo.getId())
                .setEmail(pojo.getEmail())
                .setFirstName(pojo.getFirstName())
                .setLastName(pojo.getLastName())
                .setContactNumber(pojo.getContactNumber())
                .setContactFacebook(pojo.getContactFacebook())
                .setCountry(pojo.getCountry())
                .setCity(pojo.getCity())
                .setProfilePicture(pojo.getProfilePicture())
                .setDateOfBirth(pojo.getDateOfBirth())
                .setDateOfRegistration(pojo.getDateOfRegistration())
                .setProfileConfirmed(pojo.getProfileConfirmed())
                .setGender(pojo.getGender())
                .setCoins(pojo.getCoins())
                .setPassword(pojo.getPassword());
    }

    public UserLightViewModel lightModelFromUser(User user) {
        return new UserLightViewModel()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setProfilePicture(user.getProfilePicture());
    }

    public InteractionModel interactionModelForUser(User user, String resource) {
        return new InteractionModel()
                .setUpvoted(user.getUpvotes().contains(resource))
                .setReported(user.getReports().contains(resource));
    }

}
