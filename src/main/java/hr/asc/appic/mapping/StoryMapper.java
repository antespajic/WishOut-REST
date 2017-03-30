package hr.asc.appic.mapping;

import java.util.Date;

import org.springframework.stereotype.Service;

import hr.asc.appic.controller.model.StoryExportModel;
import hr.asc.appic.controller.model.StoryViewModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.persistence.model.Story;

@Service
public class StoryMapper implements Mapper<Story, StoryViewModel> {

	@Override
	public Story modelToPojo(StoryViewModel model) {
		Story s = new Story();
		s.setCreated(model.getCreated() == null ? new Date() : model.getCreated())
			.setDescription(model.getDescription())
			.setPictures(model.getPictures())
			.setReportCount(model.getReportCount());
		
		return s;
	}

	@Override
	public StoryViewModel pojoToModel(Story pojo) {
		StoryViewModel svm = new StoryViewModel();
		svm.setCreated(pojo.getCreated())
			.setCreatorId(pojo.getCreator().getId())
			.setSponsorId(pojo.getSponsor().getId())
			.setWishId(pojo.getWish().getId())
			.setDescription(pojo.getDescription())
			.setPictures(pojo.getPictures())
			.setReportCount(pojo.getReportCount());
		
		return svm;
	}

	public StoryExportModel pojoToExportModel(StoryViewModel story, UserLightViewModel creator, UserLightViewModel sponsor) {
		return new StoryExportModel()
				.setStory(story)
				.setCreator(creator)
				.setSponsor(sponsor);
	}

}
