package hr.asc.appic.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.elasticsearch.model.StoryElasticModel;
import hr.asc.appic.elasticsearch.model.WishElasticModel;
import hr.asc.appic.service.SearchService;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired private SearchService searchService;
	
	@RequestMapping(
			path = "/story",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeferredResult<ResponseEntity<Collection<StoryElasticModel>>> searchStories(
			@RequestParam int index,
			@RequestParam int size,
			@RequestParam String query) {
		return searchService.searchStories(query, index, size);
	}
	
	@RequestMapping(
			path = "/wish",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeferredResult<ResponseEntity<Collection<WishElasticModel>>> searchWishes(
			@RequestParam int index,
			@RequestParam int size,
			@RequestParam String query) {
		return searchService.searchWishes(query, index, size);
	}
}
