package hr.asc.appic.service.image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;

import hr.asc.appic.controller.model.ImagePathModel;
import hr.asc.appic.exception.ImageUploadException;
import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import hr.asc.appic.persistence.repository.StoryRepository;
import hr.asc.appic.persistence.repository.UserRepository;
import hr.asc.appic.persistence.repository.WishRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AmazonS3ImageService implements ImageService {

    private static final String IMAGE_EXTENSION = ".png";

	@Autowired
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WishRepository wishRepository;
    @Autowired
    private StoryRepository storyRepository;

    @Value("${aws-bucket-image}")
    private String bucket;
    @Autowired
    private AmazonS3 client;
    @Autowired
    private ImagePaths imagePaths;

    // ======================================== User ======================================== //

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getUserPhoto(String id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> getUserPhotoJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    return new ImagePathModel(id, user.getProfilePicture());
                }
        );

        submitImageJob(getUserPhotoJob, result);
        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> setUserPhoto(String id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> setUserPhotoJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);

                    if (user.getProfilePicture() != null) {
                        deleteImage(imagePaths.deleteUrl(user));
                    }

                    String imagePath = imagePaths.uploadUrl(user);
                    uploadImage(imagePath, image);

                    String fullImagePath = imagePaths.accessUrl(imagePath);
                    user.setProfilePicture(fullImagePath);
                    userRepository.save(user);

                    return new ImagePathModel(id, user.getProfilePicture());
                }
        );

        submitImageJob(setUserPhotoJob, result);
        return result;
    }

    @Override
    public DeferredResult<ResponseEntity> deleteUserPhoto(String id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        ListenableFuture<Void> deleteUserPhotoJob = listeningExecutorService.submit(
                () -> {
                    User user = userRepository.findById(id).get();
                    Assert.notNull(user, "Could not find user with id: " + id);
                    deleteImage(imagePaths.deleteUrl(user));
                    user.setProfilePicture(null);
                    userRepository.save(user);
                    return null;
                }
        );

        Futures.addCallback(deleteUserPhotoJob, new FutureCallback<Void>() {

            @Override
            public void onSuccess(Void voidable) {
                result.setResult(ResponseEntity.ok().build());
            }

            @Override
            public void onFailure(Throwable throwable) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred during image manipulation", throwable);
            }
        });

        return result;
    }

    // ======================================== Wish ======================================== //

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getWishPhotos(String id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> getWishPhotosJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishRepository.findById(id).get();
                    Assert.notNull(wish, "Could not find wish with id: " + id);
                    return new ImagePathModel(id, wish.getPictures());
                }
        );

        submitImageJob(getWishPhotosJob, result);
        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> addWishPhoto(String id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> addWishPhotoJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishRepository.findById(id).get();
                    Assert.notNull(wish, "Could not find wish with id: " + id);

                    String imagePath = imagePaths.uploadUrl(wish);
                    uploadImage(imagePath, image);

                    String fullImagePath = imagePaths.accessUrl(imagePath);
                    wish.getPictures().add(fullImagePath);
                    wishRepository.save(wish);

                    return new ImagePathModel(id, wish.getPictures());
                }
        );

        submitImageJob(addWishPhotoJob, result);
        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteWishPhoto(String id, String imageName) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> deleteWishPhotoJob = listeningExecutorService.submit(
                () -> {
                    Wish wish = wishRepository.findById(id).get();
                    Assert.notNull(wish, "Could not find wish with id: " + id);

                    String accessUrl = imagePaths.accessUrl(wish, imageName);
                    String deleteUrl = imagePaths.deleteUrl(wish, imageName);

                    wish.getPictures().remove(accessUrl);
                    deleteImage(deleteUrl);

                    wishRepository.save(wish);
                    return new ImagePathModel(id, wish.getPictures());
                }
        );

        submitImageJob(deleteWishPhotoJob, result);
        return result;
    }

    // ======================================== Story ======================================== //

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getStoryPhotos(String id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> getStoryPhotoJob = listeningExecutorService.submit(
                () -> {
                    Story story = storyRepository.findById(id).get();
                    Assert.notNull(story, "Could not find story with id: " + id);
                    return new ImagePathModel(id, story.getPictures());
                }
        );

        submitImageJob(getStoryPhotoJob, result);
        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> addStoryPhoto(String id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> addStoryPhotoJob = listeningExecutorService.submit(
                () -> {
                    Story story = storyRepository.findById(id).get();
                    Assert.notNull(story, "Could not find story with id: " + id);

                    String imagePath = imagePaths.uploadUrl(story);
                    uploadImage(imagePath, image);

                    String fullImagePath = imagePaths.accessUrl(imagePath);
                    story.getPictures().add(fullImagePath);
                    storyRepository.save(story);

                    return new ImagePathModel(id, story.getPictures());
                }
        );

        submitImageJob(addStoryPhotoJob, result);
        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteStoryPhoto(String id, String imageName) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        ListenableFuture<ImagePathModel> deleteStoryPhotoJob = listeningExecutorService.submit(
                () -> {
                    Story story = storyRepository.findById(id).get();
                    Assert.notNull(story, "Could not find story with id: " + id);

                    String accessUrl = imagePaths.accessUrl(story, imageName);
                    String deleteUrl = imagePaths.deleteUrl(story, imageName);

                    story.getPictures().remove(accessUrl);
                    deleteImage(deleteUrl);

                    storyRepository.save(story);
                    return new ImagePathModel(id, story.getPictures());
                }
        );

        submitImageJob(deleteStoryPhotoJob, result);
        return result;
    }

    private void submitImageJob(ListenableFuture<ImagePathModel> job,
                                DeferredResult<ResponseEntity<ImagePathModel>> result) {
        Futures.addCallback(job, new FutureCallback<ImagePathModel>() {

            @Override
            public void onSuccess(ImagePathModel model) {
                result.setResult(ResponseEntity.ok(model));
            }

            @Override
            public void onFailure(Throwable throwable) {
                result.setResult(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build());
                log.error("Error occurred during image manipulation", throwable);
            }
        });
    }

    private void uploadImage(String imagePath, MultipartFile image) {

        File imageFile = null;
        try {
            imageFile = multipartFileToFile(image);
            PutObjectRequest request = new PutObjectRequest(bucket, imagePath, imageFile);
            request.withCannedAcl(CannedAccessControlList.PublicRead);
            client.putObject(request);
        } catch (AmazonServiceException ase) {
            log.error("Request to S3 was rejected with an error response."
                    + "\nError Message:    " + ase.getMessage()
                    + "\nHTTP Status Code: " + ase.getStatusCode()
                    + "\nAWS Error Code:   " + ase.getErrorCode()
                    + "\nError Type:       " + ase.getErrorType()
                    + "\nRequest ID:       " + ase.getRequestId());
            throw new ImageUploadException(ase);
        } catch (AmazonClientException ace) {
            log.error("Error occurred during communication with S3. Error message: "
                    + ace.getMessage());
            throw new ImageUploadException(ace);
        } finally {
            if (imageFile != null) {
                imageFile.delete();
            }
        }
    }

    private void deleteImage(String imagePath) {
        try {
            DeleteObjectRequest request = new DeleteObjectRequest(bucket, imagePath);
            client.deleteObject(request);
        } catch (AmazonServiceException ase) {
            log.error("Request to S3 was rejected with an error response."
                    + "\nError Message:    " + ase.getMessage()
                    + "\nHTTP Status Code: " + ase.getStatusCode()
                    + "\nAWS Error Code:   " + ase.getErrorCode()
                    + "\nError Type:       " + ase.getErrorType()
                    + "\nRequest ID:       " + ase.getRequestId());
            throw new ImageUploadException(ase);
        } catch (AmazonClientException ace) {
            log.error("Error occurred during communication with S3. Error message: "
                    + ace.getMessage());
            throw new ImageUploadException(ace);
        }
    }

    private File multipartFileToFile(MultipartFile multipartFile) {
        try {
            File convertedFile = Files.createTempFile(
                    multipartFile.getOriginalFilename(),
                    "-image" + IMAGE_EXTENSION
            ).toFile();
            FileOutputStream fos = new FileOutputStream(convertedFile);
            fos.write(multipartFile.getBytes());
            fos.close();
            return convertedFile;
        } catch (IOException ioe) {
            log.error("Image could not be converted to file for upload. Error message: "
                    + ioe.getMessage());
            throw new ImageUploadException(ioe);
        }
    }
}
