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
import hr.asc.appic.service.FrontPageService;
import hr.asc.appic.service.FrontPageService.WishRanking;

@RestController
@RequestMapping("/frontpage")
public class FrontPageController {

	@Autowired FrontPageService frontPageService;
	
	@RequestMapping(
			path = "/wish",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeferredResult<ResponseEntity<Collection<WishElasticModel>>> getWishes(
			@RequestParam String ranking,
			@RequestParam int index,
			@RequestParam int size) {
		WishRanking wishRanking = parseRankingString(ranking);
		
		return frontPageService.getWishes(wishRanking, index, size);
	}
	
	@RequestMapping(
			path = "/story",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public DeferredResult<ResponseEntity<Collection<StoryElasticModel>>> getStories(
			@RequestParam int index,
			@RequestParam int size) {
		return frontPageService.getStories(index, size);
	}

	private WishRanking parseRankingString(String ranking) {
		switch(ranking.toLowerCase()){
		case "new":
			return WishRanking.NEW;
		case "top":
			return WishRanking.TOP;
		case "pending":
			return WishRanking.PENDING;
		default:
			throw new IllegalArgumentException();
		}
	}
}
