package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.StoryExportModel;
import hr.asc.appic.controller.model.StoryModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.elasticsearch.model.StoryElasticModel;
import hr.asc.appic.persistence.model.Story;
import org.springframework.stereotype.Service;

@Service
public class StoryMapper implements Mapper<Story, StoryModel> {

    // Note: wish, creator and sponsor objects need
    // to be set manually since database needs to be
    // queried for those objects to be retrieved.
    @Override
    public Story modelToPojo(StoryModel model) {
        return new Story()
                .setId(model.getId())
                .setDescription(model.getDescription())
                .setCreated(model.getCreated())
                .setPictures(model.getPictures())
                .setReportCount(model.getReportCount());
    }

    @Override
    public StoryModel pojoToModel(Story pojo) {
        return new StoryModel()
                .setId(pojo.getId())
                .setWishId(pojo.getWish().getId())
                .setCreatorId(pojo.getCreator().getId())
                .setSponsorId(pojo.getSponsor().getId())
                .setDescription(pojo.getDescription())
                .setCreated(pojo.getCreated())
                .setPictures(pojo.getPictures())
                .setReportCount(pojo.getReportCount());
    }

    @Override
    public void updatePojoFromModel(Story story, StoryModel model) {
        if (model.getDescription() != null) {
            story.setDescription(model.getDescription());
        }
    }

    public StoryExportModel pojoToExportModel(StoryModel story,
                                              UserLightViewModel creator,
                                              UserLightViewModel sponsor) {
        return new StoryExportModel()
                .setStory(story)
                .setCreator(creator)
                .setSponsor(sponsor);
        // In the future, interaction needs to be implemented.
    }

    public StoryElasticModel toElasticModel(Story story,
                                            UserLightViewModel creator,
                                            UserLightViewModel sponsor) {
        return StoryElasticModel.builder()
                .creator(creator)
                .sponsor(sponsor)
                .created(String.valueOf(story.getCreated().getTime()))
                .description(story.getDescription())
                .id(story.getId())
                .pictures(story.getPictures()).build();
    }
}
