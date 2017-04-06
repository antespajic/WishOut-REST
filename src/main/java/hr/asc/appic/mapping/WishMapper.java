package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.stereotype.Service;

@Service
public class WishMapper implements Mapper<Wish, WishModel> {

    // Note: user needs to be set manually, since database
    // needs to be queried to retrieve full User object.
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

    public void updateWishFromModel(Wish wish, WishModel model) {
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
    }
}
