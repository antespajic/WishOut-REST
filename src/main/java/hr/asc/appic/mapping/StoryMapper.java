package hr.asc.appic.mapping;

import java.util.Date;

import org.springframework.stereotype.Service;

import hr.asc.appic.controller.model.StoryExportModel;
import hr.asc.appic.controller.model.StoryModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.elasticsearch.model.StoryElasticModel;
import hr.asc.appic.persistence.model.Story;

@Service
public class StoryMapper implements Mapper<Story, StoryModel> {

	@Override
	public Story modelToPojo(StoryModel model) {
		return new Story()
			.setCreated(model.getCreated() == null ? new Date() : model.getCreated())
			.setDescription(model.getDescription())
			.setPictures(model.getPictures())
			.setReportCount(model.getReportCount());
	}

	@Override
	public StoryModel pojoToModel(Story pojo) {
		return new StoryModel()
			.setId(pojo.getId())
			.setCreated(pojo.getCreated())
			.setCreatorId(pojo.getCreator().getId())
			.setSponsorId(pojo.getSponsor().getId())
			.setWishId(pojo.getWish().getId())
			.setDescription(pojo.getDescription())
			.setPictures(pojo.getPictures())
			.setReportCount(pojo.getReportCount());
	}

	public StoryExportModel pojoToExportModel(StoryModel story, UserLightViewModel creator, UserLightViewModel sponsor) {
		return new StoryExportModel()
				.setStory(story)
				.setCreator(creator)
				.setSponsor(sponsor);
	}
	
	public StoryElasticModel toElasticModel(Story story, UserLightViewModel creator, UserLightViewModel sponsor) {
		return StoryElasticModel.builder()
		.creator(creator)
		.sponsor(sponsor)
		.created(String.valueOf(story.getCreated().getTime()))
		.description(story.getDescription())
		.id(story.getId())
		.pictures(story.getPictures()).build();
	}

}
