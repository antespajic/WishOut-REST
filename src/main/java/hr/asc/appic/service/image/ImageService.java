package hr.asc.appic.service.image;

import hr.asc.appic.controller.model.ImagePathModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

public interface ImageService {

    DeferredResult<ResponseEntity<ImagePathModel>> getUserPhoto(BigInteger id);

    DeferredResult<ResponseEntity<ImagePathModel>> setUserPhoto(BigInteger id, MultipartFile image);

    DeferredResult<ResponseEntity> deleteUserPhoto(BigInteger id);

    DeferredResult<ResponseEntity<ImagePathModel>> getWishPhotos(BigInteger id);

    DeferredResult<ResponseEntity<ImagePathModel>> addWishPhoto(BigInteger id, MultipartFile image);

    DeferredResult<ResponseEntity<ImagePathModel>> deleteWishPhoto(BigInteger id, String imagePath);

    DeferredResult<ResponseEntity<ImagePathModel>> getStoryPhotos(BigInteger id);

    DeferredResult<ResponseEntity<ImagePathModel>> addStoryPhoto(BigInteger id, MultipartFile image);

    DeferredResult<ResponseEntity<ImagePathModel>> deleteStoryPhoto(BigInteger id, String imagePath);
}
