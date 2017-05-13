package hr.asc.appic.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.elasticsearch.model.StoryElasticModel;
import hr.asc.appic.elasticsearch.model.WishElasticModel;
import hr.asc.appic.elasticsearch.repository.StoryElasticRepository;
import hr.asc.appic.elasticsearch.repository.WishElasticRepository;
import hr.asc.appic.mapping.WishMapper;

@Service
public class FrontPageService {

	public enum WishRanking { NEW, TOP, PENDING	}
	
	@Autowired private StoryElasticRepository storyRepository;
	@Autowired private WishElasticRepository wishRepository;
	@Autowired private WishMapper wishMapper;
	
	public DeferredResult<ResponseEntity<Collection<WishElasticModel>>> getWishes(WishRanking ranking, int index, int size) {
		DeferredResult<ResponseEntity<Collection<WishElasticModel>>> res = new DeferredResult<>();
		List<WishElasticModel> wishes;
		switch(ranking) {
		case NEW:
			wishes = wishRepository.findAll(
					new PageRequest(index, size, new Sort(Sort.Direction.DESC, "created")))
			.getContent();
			break;
		case TOP:
			wishes = wishRepository.findAll(
					new PageRequest(index, size, new Sort(Sort.Direction.DESC, "upvoteCount")))
			.getContent();
			break;
		case PENDING:
			wishes = wishRepository.findByState(
					1, new PageRequest(index, size, new Sort(Sort.Direction.DESC, "created")))
					.getContent();
			break;
		default:
			wishes = Collections.emptyList();
		}
		wishes.stream().forEach(wishMapper::calculateTimeLeftForWish);
		res.setResult(ResponseEntity.ok(wishes));
		return res;
	}
	
	public DeferredResult<ResponseEntity<Collection<StoryElasticModel>>> getStories(int index, int size) {
		DeferredResult<ResponseEntity<Collection<StoryElasticModel>>> res = new DeferredResult<>();
		List<StoryElasticModel> wishes =
				storyRepository.findAll(
						new PageRequest(index, size, new Sort(Sort.Direction.DESC, "created")))
				.getContent();
		res.setResult(ResponseEntity.ok(wishes));
		return res;
	}
	
}
