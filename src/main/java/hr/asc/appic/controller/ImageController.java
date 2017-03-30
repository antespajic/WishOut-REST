package hr.asc.appic.controller;

import hr.asc.appic.controller.model.ImagePathModel;
import hr.asc.appic.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // ==================== USER ==================== //

    @RequestMapping(
            value = "/user/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> getUserPhoto(
            @PathVariable("id") BigInteger id) {
        return imageService.getUserPhoto(id);
    }

    @RequestMapping(
            value = "/user/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> setUserPhoto(
            @PathVariable("id") BigInteger id,
            @RequestParam("image") MultipartFile image) {
        return imageService.setUserPhoto(id, image);
    }

    @RequestMapping(
            value = "/user/{id}/{imageName}",
            method = RequestMethod.DELETE
    )
    public DeferredResult<ResponseEntity> deleteUserPhoto(
            @PathVariable("id") BigInteger id,
            @PathVariable("imageName") String imageName) {
        return imageService.deleteUserPhoto(id, imageName);
    }

    // ==================== WISH ==================== //

    @RequestMapping(
            value = "/wish/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> getWishPhotos(
            @PathVariable("id") BigInteger id) {
        return imageService.getWishPhotos(id);
    }

    @RequestMapping(
            value = "/wish/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> addWishPhoto(
            @PathVariable("id") BigInteger id,
            @RequestParam("image") MultipartFile image) {
        return imageService.addWishPhoto(id, image);
    }

    @RequestMapping(
            value = "/wish/{id}/{imageName}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteWishPhoto(
            @PathVariable("id") BigInteger id,
            @PathVariable("imageName") String imageName) {
        return imageService.deleteWishPhoto(id, imageName);
    }

    // ==================== STORY ==================== //

    @RequestMapping(
            value = "/story/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> getStoryPhotos(
            @PathVariable("id") BigInteger id) {
        return imageService.getStoryPhotos(id);
    }

    @RequestMapping(
            value = "/story/{id}",
            method = RequestMethod.PUT,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> addStoryPhoto(
            @PathVariable("id") BigInteger id,
            @RequestParam("image") MultipartFile image) {
        return imageService.addStoryPhoto(id, image);
    }

    @RequestMapping(
            value = "/story/{id}/{imageName}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<ImagePathModel>> deleteStoryPhoto(
            @PathVariable("id") BigInteger id,
            @PathVariable("imageName") String imageName) {
        return imageService.deleteStoryPhoto(id, imageName);
    }
}
