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
                .setId(model.getId())
                .setEmail(model.getEmail())
                .setPassword(model.getPassword())
                .setFirstName(model.getFirstName())
                .setLastName(model.getLastName())
                .setCity(model.getCity())
                .setCountry(model.getCountry())
                .setGender(model.getGender())
                .setDateOfBirth(model.getDateOfBirth())
                .setDateOfRegistration(model.getDateOfRegistration())
                .setProfilePicture(model.getProfilePicture())
                .setContactNumber(model.getContactNumber())
                .setContactFacebook(model.getContactFacebook())
                .setProfileConfirmed(model.getProfileConfirmed())
                .setCoins(model.getCoins());
    }

    @Override
    public UserModel pojoToModel(User pojo) {
        return new UserModel()
                .setId(pojo.getId())
                .setEmail(pojo.getEmail())
                .setPassword(pojo.getPassword())
                .setFirstName(pojo.getFirstName())
                .setLastName(pojo.getLastName())
                .setCity(pojo.getCity())
                .setCountry(pojo.getCountry())
                .setGender(pojo.getGender())
                .setDateOfBirth(pojo.getDateOfBirth())
                .setDateOfRegistration(pojo.getDateOfRegistration())
                .setProfilePicture(pojo.getProfilePicture())
                .setContactNumber(pojo.getContactNumber())
                .setContactFacebook(pojo.getContactFacebook())
                .setProfileConfirmed(pojo.getProfileConfirmed())
                .setCoins(pojo.getCoins());
    }

    @Override
    public void updatePojoFromModel(User user, UserModel viewModel) {
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
