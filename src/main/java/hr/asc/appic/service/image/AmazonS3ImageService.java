package hr.asc.appic.service.image;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import hr.asc.appic.exception.ContentCheck;
import hr.asc.appic.exception.ImageUploadException;
import hr.asc.appic.service.RepoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public DeferredResult<String> getUserPhoto(BigInteger id) {
        DeferredResult<String> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    result.setResult(ImagePaths.originForUser(u));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<String> setUserPhoto(BigInteger id, MultipartFile image) {
        DeferredResult<String> result = new DeferredResult<>();

        repoProvider.userRepository.findById(id).addCallback(
                u -> {
                    ContentCheck.requireNonNull(id, u);
                    String imagePath = ImagePaths.destinationForUser(u);
                    uploadImage(imagePath, image);
                    u.setProfilePicture(imagePath);
                    result.setResult(imagePath);
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<String[]> getWishPhotos(BigInteger id) {
        DeferredResult<String[]> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    result.setResult(ImagePaths.originsForWish(w));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<String> addWishPhoto(BigInteger id, MultipartFile image) {
        DeferredResult<String> result = new DeferredResult<>();

        repoProvider.wishRepository.findById(id).addCallback(
                w -> {
                    ContentCheck.requireNonNull(id, w);
                    String imagePath = ImagePaths.destinationForWish(w);
                    uploadImage(imagePath, image);
                    w.getPictures().add(imagePath);
                    result.setResult(imagePath);
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<String[]> getStoryPhotos(BigInteger id) {
        DeferredResult<String[]> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    result.setResult(ImagePaths.originsForStory(s));
                },
                e -> {
                    // TODO
                }
        );

        return result;
    }

    @Override
    public DeferredResult<String> addStoryPhoto(BigInteger id, MultipartFile image) {
        DeferredResult<String> result = new DeferredResult<>();

        repoProvider.storyRepository.findById(id).addCallback(
                s -> {
                    ContentCheck.requireNonNull(id, s);
                    String imagePath = ImagePaths.destinationForStory(s);
                    uploadImage(imagePath, image);
                    s.getPictures().add(imagePath);
                    result.setResult(imagePath);
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
