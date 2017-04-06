package hr.asc.appic.service;

import java.util.Collection;
import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.elasticsearch.model.StoryElasticModel;
import hr.asc.appic.elasticsearch.model.WishElasticModel;
import hr.asc.appic.elasticsearch.repository.StoryElasticRepository;
import hr.asc.appic.elasticsearch.repository.WishElasticRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SearchService {

	@Autowired private WishElasticRepository wishElasticRepository;
	@Autowired private StoryElasticRepository storyElasticRepository;
	
	public DeferredResult<ResponseEntity<Collection<WishElasticModel>>> searchWishes(String query, int index, int size) {
		log.info("In searchWishes");
		
		DeferredResult<ResponseEntity<Collection<WishElasticModel>>> res = new DeferredResult<>();
		
		List<WishElasticModel> searchResults = wishElasticRepository.search(
				QueryBuilders.queryStringQuery(query).field("title").field("description"), 
				new PageRequest(index, size)
				).getContent();
		
		res.setResult(ResponseEntity.ok(searchResults));
		return res;
	}
	
	public DeferredResult<ResponseEntity<Collection<StoryElasticModel>>> searchStories(String query, int index, int size) {
		log.info("In searchStories");
		
		DeferredResult<ResponseEntity<Collection<StoryElasticModel>>> res = new DeferredResult<>();
		
		List<StoryElasticModel> searchResults = storyElasticRepository.search(
				QueryBuilders.queryStringQuery(query).field("description"), 
				new PageRequest(index, size)
				).getContent();
		
		res.setResult(ResponseEntity.ok(searchResults));
		return res;
	}
}
