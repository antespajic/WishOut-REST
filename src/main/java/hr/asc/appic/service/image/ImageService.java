package hr.asc.appic.service.image;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

public interface ImageService {

    DeferredResult<String> getUserPhoto(BigInteger id);

    DeferredResult<String> setUserPhoto(BigInteger id, MultipartFile image);

    DeferredResult<String[]> getWishPhotos(BigInteger id);

    DeferredResult<String> addWishPhoto(BigInteger id, MultipartFile image);

    DeferredResult<String[]> getStoryPhotos(BigInteger id);

    DeferredResult<String> addStoryPhoto(BigInteger id, MultipartFile image);
}
