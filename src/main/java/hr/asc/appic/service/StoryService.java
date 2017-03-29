package hr.asc.appic.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.controller.model.StoryViewModel;
import hr.asc.appic.mapping.Mapper;
import hr.asc.appic.mapping.StoryMapper;
import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.repository.StoryRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoryService {

	@Autowired
	private StoryRepository storyRepository;
	
	private Mapper<Story, StoryViewModel> map = new StoryMapper();
	
	public DeferredResult<ResponseEntity<StoryViewModel>> create(StoryViewModel svm) {
		DeferredResult<ResponseEntity<StoryViewModel>> res = new DeferredResult<>();
		
		
		
		storyRepository.save(map.modelToPojo(svm)).addCallback(
				response -> res.setResult(ResponseEntity.ok(map.pojoToModel(response))),
				error -> {
					res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
					log.error("Error while saving", error);
				});
		return res;
	}
	
	public DeferredResult<ResponseEntity<StoryViewModel>> get(Long id) {
		DeferredResult<ResponseEntity<StoryViewModel>> res = new DeferredResult<>();
		storyRepository.findById(BigInteger.valueOf(id)).addCallback(
				response -> {
					Assert.notNull(response, "Couldn't find a story with provided id " + id);
					res.setResult(ResponseEntity.ok(map.pojoToModel(response)));
				},
				error -> {
					res.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
					log.error("Error while getting story", error);
				});
		return res;
	}
}
