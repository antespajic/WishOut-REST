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
import java.math.BigInteger;

@Slf4j
@Service
public class AmazonS3ImageService implements ImageService {

    @Value("${aws-bucket-image}")
    private String bucket;
    @Autowired
    private AmazonS3 client;
    @Autowired
    private RepoProvider repoProvider;

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getUserPhoto(BigInteger id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrl(u))));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> setUserPhoto(BigInteger id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    String imagePath = ImagePaths.destinationUrl(u);
                    uploadImage(imagePath, image);
                    u.setProfilePicture(imagePath);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrl(u))));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity> deleteUserPhoto(BigInteger id) {
        DeferredResult<ResponseEntity> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    deleteImage(u.getProfilePicture());
                    u.setProfilePicture(null);
                    result.setResult(ResponseEntity.ok().build());
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getWishPhotos(BigInteger id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrls(w))));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> addWishPhoto(BigInteger id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    String imagePath = ImagePaths.destinationUrls(w);
                    uploadImage(imagePath, image);
                    w.getPictures().add(imagePath);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrls(w))));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteWishPhoto(BigInteger id, String imagePath) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    String path = ImagePaths.accessUrlFromDestinationUrl(imagePath);

                    if (w.getPictures().contains(path)) {
                        w.getPictures().remove(path);
                        deleteImage(path);
                        result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrls(w))));
                    } else {
                        result.setResult(ResponseEntity.badRequest().build());
                    }
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> getStoryPhotos(BigInteger id) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrls(s))));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> addStoryPhoto(BigInteger id, MultipartFile image) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    String imagePath = ImagePaths.destinationUrls(s);
                    uploadImage(imagePath, image);
                    s.getPictures().add(imagePath);
                    result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrls(s))));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteStoryPhoto(BigInteger id, String imagePath) {
        DeferredResult<ResponseEntity<ImagePathModel>> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    String path = ImagePaths.accessUrlFromDestinationUrl(imagePath);

                    if (s.getPictures().contains(path)) {
                        s.getPictures().remove(path);
                        deleteImage(path);
                        result.setResult(ResponseEntity.ok(new ImagePathModel(id, ImagePaths.accessUrls(s))));
                    } else {
                        result.setResult(ResponseEntity.badRequest().build());
                    }
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    private void uploadImage(String imagePath, MultipartFile image) {
        try {
            PutObjectRequest request = new PutObjectRequest(bucket, imagePath, multipartFileToFile(image));
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
            File convertedFile = new File(multipartFile.getOriginalFilename());
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
