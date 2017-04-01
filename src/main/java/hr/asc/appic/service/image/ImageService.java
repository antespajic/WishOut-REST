package hr.asc.appic.service.image;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import hr.asc.appic.controller.model.ImagePathModel;

public interface ImageService {

    DeferredResult<ResponseEntity<ImagePathModel>> getUserPhoto(String id);

    DeferredResult<ResponseEntity<ImagePathModel>> setUserPhoto(String id, MultipartFile image);

    DeferredResult<ResponseEntity> deleteUserPhoto(String id, String imageName);

    DeferredResult<ResponseEntity<ImagePathModel>> getWishPhotos(String id);

    DeferredResult<ResponseEntity<ImagePathModel>> addWishPhoto(String id, MultipartFile image);

    DeferredResult<ResponseEntity<ImagePathModel>> deleteWishPhoto(String id, String imageName);

    DeferredResult<ResponseEntity<ImagePathModel>> getStoryPhotos(String id);

    DeferredResult<ResponseEntity<ImagePathModel>> addStoryPhoto(String id, MultipartFile image);

    DeferredResult<ResponseEntity<ImagePathModel>> deleteStoryPhoto(String id, String imageName);
}
