package hr.asc.appic.controller;

import hr.asc.appic.service.image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public DeferredResult<String> getUserPhoto(
            @PathVariable("id") BigInteger id) {
        return imageService.getUserPhoto(id);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public DeferredResult<String> setUserPhoto(
            @PathVariable("id") BigInteger id,
            @RequestParam("image") MultipartFile image) {
        return imageService.setUserPhoto(id, image);
    }

    // ==================== WISH ==================== //

    @RequestMapping(value = "/wish/{id}", method = RequestMethod.GET)
    public DeferredResult<String[]> getWishPhotos(
            @PathVariable("id") BigInteger id) {
        return imageService.getWishPhotos(id);
    }

    @RequestMapping(value = "/wish/{id}", method = RequestMethod.PUT)
    public DeferredResult<String> addWishPhoto(
            @PathVariable("id") BigInteger id,
            @RequestParam("image") MultipartFile image) {
        return imageService.addWishPhoto(id, image);
    }

    // ==================== STORY ==================== //

    @RequestMapping(value = "/story/{id}", method = RequestMethod.GET)
    public DeferredResult<String[]> getStoryPhotos(
            @PathVariable("id") BigInteger id) {
        return imageService.getStoryPhotos(id);
    }

    @RequestMapping(value = "/story/{id}", method = RequestMethod.PUT)
    public DeferredResult<String> addStoryPhoto(
            @PathVariable("id") BigInteger id,
            @RequestParam("image") MultipartFile image) {
        return imageService.addStoryPhoto(id, image);
    }
}
