package hr.asc.appic.controller;

import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@RestController
@RequestMapping("/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity> getWish(@PathVariable("id") BigInteger id,
                                                  @RequestParam("lower") Long lower,
                                                  @RequestParam(value = "upper") Long upper) {

        return null;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity> createWish(@RequestBody WishModel model) {
        return wishService.createWish(model);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity> updateWish(@PathVariable("id") BigInteger id,
                                                     @RequestBody WishModel model) {
        return wishService.updateWish(id, model);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult deleteWish(@PathVariable("id") BigInteger id) {
        return wishService.deleteWish(id);
    }
}
