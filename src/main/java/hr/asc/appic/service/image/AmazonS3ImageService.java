package hr.asc.appic.service.image;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hr.asc.appic.controller.model.ImagePathModel;
import hr.asc.appic.exception.ContentCheck;
import hr.asc.appic.exception.ImageUploadException;
import hr.asc.appic.service.RepoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@Service
public class AmazonS3ImageService implements ImageService {

    @Value("${aws-bucket-image}")
    private String bucket;
    @Autowired
    private AmazonS3 client;
    @Autowired
    private RepoProvider repoProvider;

    @Autowired
    private ImagePaths imagePaths;

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getUserPhoto(String id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, u.getProfilePicture())));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> setUserPhoto(String id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    String imagePath = imagePaths.uploadUrl(u);
                    uploadImage(imagePath, image);
                    String fullImagePath = imagePaths.accessUrl(imagePath);
                    u.setProfilePicture(fullImagePath);
                    repoProvider.userRepository.save(u);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, u.getProfilePicture())));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity> deleteUserPhoto(String id, String imagePath) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    String deleteUrl = imagePaths.deleteUrl(u);
                    deleteImage(deleteUrl);
                    u.setProfilePicture(null);
                    repoProvider.userRepository.save(u);
                    result.setResult(ResponseEntity.ok().build());
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getWishPhotos(String id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, w.getPictures())));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> addWishPhoto(String id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    String imagePath = imagePaths.uploadUrl(w);
                    uploadImage(imagePath, image);
                    String fullImagePath = imagePaths.accessUrl(imagePath);
                    w.getPictures().add(fullImagePath);
                    repoProvider.wishRepository.save(w);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, w.getPictures())));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteWishPhoto(String id, String imagePath) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    String deleteUrl = imagePaths.deleteUrl(w, imagePath);

                    if (w.getPictures().contains(imagePath)) {
                        w.getPictures().remove(imagePath);
                        deleteImage(deleteUrl);
                        result.setResult(ResponseEntity.ok(new ImagePathModel(id, w.getPictures())));
                    } else {
                        result.setResult(ResponseEntity.badRequest().build());
                    }

                    repoProvider.wishRepository.save(w);
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getStoryPhotos(String id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, s.getPictures())));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> addStoryPhoto(String id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    String imagePath = imagePaths.uploadUrl(s);
                    uploadImage(imagePath, image);
                    String fullImagePath = imagePaths.accessUrl(imagePath);
                    s.getPictures().add(fullImagePath);
                    repoProvider.storyRepository.save(s);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, s.getPictures())));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteStoryPhoto(String id, String imagePath) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    String deleteUrl = imagePaths.deleteUrl(s, imagePath);

                    if (s.getPictures().contains(imagePath)) {
                        s.getPictures().remove(imagePath);
                        deleteImage(deleteUrl);
                        result.setResult(ResponseEntity.ok(new ImagePathModel(id, s.getPictures())));
                    } else {
                        result.setResult(ResponseEntity.badRequest().build());
                    }

                    repoProvider.storyRepository.save(s);
                },
                e -> {
                    // TODO
                }
        );

        return result;
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
                    "-image"
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
