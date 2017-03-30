package hr.asc.appic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.controller.model.StoryExportModel;
import hr.asc.appic.controller.model.StoryViewModel;
import hr.asc.appic.service.StoryService;

@RestController
@RequestMapping("/story")
public class StoryController {

	@Autowired private StoryService storyService;
	
	@RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<StoryViewModel>> createStory(@RequestBody StoryViewModel viewModel) {
		return storyService.create(viewModel);
	}
	
	@RequestMapping(
			path = "/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeferredResult<ResponseEntity<StoryExportModel>> getStory(@RequestParam String id) {
		return storyService.get(id);
	}
	
	@RequestMapping(
			path = "/{id}",
			method = RequestMethod.DELETE,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeferredResult<ResponseEntity<?>> deleteStory(@RequestParam String id) {
		return storyService.delete(id);
	}
}
