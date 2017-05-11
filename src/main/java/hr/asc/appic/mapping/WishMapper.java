package hr.asc.appic.mapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.elasticsearch.model.WishElasticModel;
import hr.asc.appic.persistence.model.Wish;

@Service
public class WishMapper implements Mapper<Wish, WishModel> {

    // Note: user needs to be set manually, since database
    // needs to be queried to retrieve full User object.
    @Override
    public Wish modelToPojo(WishModel model) {
        return new Wish()
                .setId(model.getId())
                .setTitle(model.getTitle())
                .setDescription(model.getDescription())
                .setCategories(model.getCategories())
                .setPictures(model.getPictures())
                .setCreated(model.getCreated())
                .setState(model.getState())
                .setUpvoteCount(model.getUpvoteCount())
                .setReportCount(model.getReportCount());
    }

    @Override
    public WishModel pojoToModel(Wish wish) {
        return new WishModel()
                .setId(wish.getId())
                .setUserId(wish.getUser().getId())
                .setTitle(wish.getTitle())
                .setDescription(wish.getDescription())
                .setCategories(wish.getCategories())
                .setPictures(wish.getPictures())
                .setCreated(wish.getCreated())
                .setState(wish.getState())
                .setUpvoteCount(wish.getUpvoteCount())
                .setReportCount(wish.getReportCount());
    }

    @Override
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
    }

    public WishElasticModel toElasticModel(Wish wish, UserLightViewModel creator) {
        return WishElasticModel.builder()
        		.id(wish.getId())
                .creator(creator)
                .created(String.valueOf(wish.getCreated().getTime()))
                .categories(wish.getCategories())
                .pictures(wish.getPictures())
                .title(wish.getTitle())
                .state(wish.getState())
                .upvoteCount(wish.getUpvoteCount())
                .description(wish.getDescription()).build();
    }
    
    public void calculateTimeLeftForWish(WishElasticModel wm) {
    	//vrijemeStvaranja(timestamp) + 3 dana - sadašnjeVrijeme(timestamp) + (brojUpvoteova*15minuta)
    	LocalDateTime localDateTime = Instant.ofEpochSecond( Long.parseLong(wm.getCreated()))
    			.atZone(ZoneId.systemDefault()).toLocalDateTime();
    	
    	localDateTime = localDateTime.plusDays(3).plusSeconds(wm.getUpvoteCount()*15*60);
    	
    	wm.setTimeLeft(ChronoUnit.SECONDS.between(LocalDateTime.now(), localDateTime));
	}
    
    public void calculateTimeLeftForWish(WishModel wm) {
    	//vrijemeStvaranja(timestamp) + 3 dana - sadašnjeVrijeme(timestamp) + (brojUpvoteova*15minuta)
    	
    	LocalDateTime localDateTime = wm.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    	
    	localDateTime = localDateTime.plusDays(3).plusSeconds(wm.getUpvoteCount()*15*60);
    	
    	wm.setTimeLeft(ChronoUnit.SECONDS.between(LocalDateTime.now(), localDateTime));
	}
}
