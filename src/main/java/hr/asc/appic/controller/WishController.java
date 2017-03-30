package hr.asc.appic.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.service.WishService;

@RestController
@RequestMapping("/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<ResponseEntity> getWish(@PathVariable("id") String id,
                                                  @RequestParam("index") Integer index,
                                                  @RequestParam(value = "size") Integer size) {
        return wishService.getWish(index, size, id);
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
    public DeferredResult<ResponseEntity> updateWish(@PathVariable("id") String id,
                                                     @RequestBody WishModel model) {
        return wishService.updateWish(id, model);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult deleteWish(@PathVariable("id") String id) {
        return wishService.deleteWish(id);
    }
}
