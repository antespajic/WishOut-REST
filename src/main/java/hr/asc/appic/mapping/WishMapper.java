package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.elasticsearch.model.WishElasticModel;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.stereotype.Service;

@Service
public class WishMapper implements Mapper<Wish, WishModel> {

    @Override
    public Wish modelToPojo(WishModel model) {
        Wish wish = new Wish();
        wish.setId(model.getId())
                .setTitle(model.getTitle())
                .setDescription(model.getDescription())
                .setCategories(model.getCategories())
                .setPictures(model.getPictures())
                .setCreated(model.getCreated())
                .setState(model.getState())
                .setUpvoteCount(model.getUpvoteCount())
                .setReportCount(model.getReportCount());
        return wish;
    }

    @Override
    public WishModel pojoToModel(Wish wish) {
        WishModel model = new WishModel();
        model.setId(wish.getId())
                .setUserId(wish.getUser().getId())
                .setTitle(wish.getTitle())
                .setDescription(wish.getDescription())
                .setCategories(wish.getCategories())
                .setPictures(wish.getPictures())
                .setCreated(wish.getCreated())
                .setState(wish.getState())
                .setUpvoteCount(wish.getUpvoteCount())
                .setReportCount(wish.getReportCount());
        return model;
    }
    
    public WishElasticModel toElasticModel(Wish wish, UserLightViewModel creator) {
    	return WishElasticModel.builder()
    			.creator(creator)
    			.created(String.valueOf(wish.getCreated().getTime()))
    			.categories(wish.getCategories())
    			.id(wish.getId())
    			.title(wish.getTitle())
    			.state(wish.getState())
    			.upvoteCount(wish.getUpvoteCount())
    			.description(wish.getDescription()).build();
    }

    public void updatePojoFromModel(Wish wish, WishModel model) {

        if (model.getTitle() != null) {
            wish.setTitle(model.getTitle());
        }
        if (model.getDescription() != null) {
            wish.setDescription(model.getDescription());
        }
        if (model.getCategories() != null) {
            wish.setCategories(model.getCategories());
        }
        if (model.getState() != null) {
            wish.setState(model.getState());
        }
        if (model.getUpvoteCount() != null) {
            wish.setUpvoteCount(model.getUpvoteCount());
        }
        if (model.getReportCount() != null) {
            wish.setReportCount(model.getReportCount());
        }
    }
}
