package hr.asc.appic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import hr.asc.appic.controller.model.StoryExportModel;
import hr.asc.appic.controller.model.StoryViewModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.mapping.StoryMapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.StoryRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;
	@Autowired
	private WishRepository wishRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ListeningExecutorService listeningExecutorService;
	
	@Autowired private StoryMapper map;
	@Autowired private UserMapper userMapper;
	
	public DeferredResult<ResponseEntity<StoryViewModel>> create(StoryViewModel svm) {
		DeferredResult<ResponseEntity<StoryViewModel>> res = new DeferredResult<>();
		
		ListenableFuture<ResponseEntity<StoryViewModel>> createStory = listeningExecutorService
				.submit(()-> {
					Story st = map.modelToPojo(svm);
					
					User u = userRepository.findById(svm.getCreatorId()).get();
					Wish w = wishRepository.findById(svm.getWishId()).get();
					User s = userRepository.findById(svm.getSponsorId()).get();
					
					st.setWish(w)
						.setCreator(u)
						.setSponsor(s);
				
					Story stori = storyRepository.save(st).get();
					
					return ResponseEntity.ok(map.pojoToModel(stori));
				}
		);
		
		Futures.addCallback(createStory, new FutureCallback<ResponseEntity<StoryViewModel>>() {

			@Override
			public void onSuccess(ResponseEntity<StoryViewModel> result) {
				res.setResult(result);
			}
		
			@Override
			public void onFailure(Throwable t) {
				log.error("Error in creating story", t);
			}
			
		});
		
		return res;
	}
	
	public DeferredResult<ResponseEntity<StoryExportModel>> get(String id) {
		DeferredResult<ResponseEntity<StoryExportModel>> res = new DeferredResult<>();
		storyRepository.findById(id).addCallback(
				response -> {
					Assert.notNull(response, "Couldn't find a story with provided id " + id);
					UserLightViewModel creator = userMapper.lightModelFromUser(response.getCreator());
					UserLightViewModel sponsor = userMapper.lightModelFromUser(response.getSponsor());
					StoryViewModel story = map.pojoToModel(response);
					res.setResult(ResponseEntity.ok(map.pojoToExportModel(story, creator, sponsor)));
				},
				error -> {
					res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
					log.error("Error while getting story", error);
				});
		return res;
	}

	public DeferredResult<ResponseEntity<?>> delete(String id) {
		DeferredResult<ResponseEntity<?>> res = new DeferredResult<>();
		storyRepository.delete(id).addCallback(
				response -> res.setResult(ResponseEntity.ok().build()),
				error -> {
					res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
					log.error("Error deleting story", error);
				});
		return res;
	}
}
