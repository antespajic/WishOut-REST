package hr.asc.appic.service;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import hr.asc.appic.controller.model.StoryExportModel;
import hr.asc.appic.controller.model.StoryModel;
import hr.asc.appic.controller.model.UserLightViewModel;
import hr.asc.appic.elasticsearch.repository.StoryElasticRepository;
import hr.asc.appic.mapping.StoryMapper;
import hr.asc.appic.mapping.UserMapper;
import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.StoryRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@Service
public class StoryService {

    @Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private StoryElasticRepository storyElasticRepository;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StoryMapper storyMapper;

    public DeferredResult<ResponseEntity<StoryModel>> create(StoryModel svm) {
        DeferredResult<ResponseEntity<StoryModel>> result = new DeferredResult<>();

        ListenableFuture<StoryModel> createStoryJob = listeningExecutorService.submit(() -> {
                    Story story = storyMapper.modelToPojo(svm);

                    Wish wish = wishRepository.findById(svm.getWishId()).get();
                    User creator = userRepository.findById(svm.getCreatorId()).get();
                    User sponsor = userRepository.findById(svm.getSponsorId()).get();

                    story.setWish(wish).setCreator(creator).setSponsor(sponsor);
                    story = storyRepository.save(story).get();

                    creator.getStories().add(story);
                    userRepository.save(creator);

                    storyElasticRepository.save(storyMapper.toElasticModel(
                            story,
                            userMapper.lightModelFromUser(creator),
                            userMapper.lightModelFromUser(sponsor))
                    );

                    return storyMapper.pojoToModel(story);
                }
        );

        Futures.addCallback(createStoryJob, new FutureCallback<StoryModel>() {

            @Override
            public void onSuccess(StoryModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while creating story object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity<StoryExportModel>> getStory(String id) {
        DeferredResult<ResponseEntity<StoryExportModel>> result = new DeferredResult<>();

        ListenableFuture<StoryExportModel> getStoryJob = listeningExecutorService.submit(
                () -> {
                    Story story = storyRepository.findById(id).get();
                    Assert.notNull(story, "Could not find story with id: " + id);

                    UserLightViewModel creator = userMapper.lightModelFromUser(story.getCreator());
                    UserLightViewModel sponsor = userMapper.lightModelFromUser(story.getSponsor());

                    StoryModel model = storyMapper.pojoToModel(story);
                    return storyMapper.pojoToExportModel(model, creator, sponsor);
                    // In the future update, interaction model needs to be set.
                }
        );

        Futures.addCallback(getStoryJob, new FutureCallback<StoryExportModel>() {

            @Override
            public void onSuccess(StoryExportModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while retrieving story object", t);
            }
        });

        return result;
    }

    public DeferredResult<ResponseEntity> updateStory(String id, StoryModel model) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> updateStoryJob = listeningExecutorService.submit(
                () -> {
                    Story story = storyRepository.findById(id).get();
                    Assert.notNull(story, "Could not find story with id: " + id);

                    storyMapper.updatePojoFromModel(story, model);
                    storyRepository.save(story);

                    return null;
                }
        );

        Futures.addCallback(updateStoryJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable t) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred while updating story object", t);
            }
        });

        return result;
    }
}
