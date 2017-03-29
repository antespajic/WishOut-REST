package hr.asc.appic.controller;

import hr.asc.appic.controller.model.WishModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@RestController
@RequestMapping("/wish")
public class WishController {

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<WishModel> getWish(@PathVariable("id") BigInteger id) {

        return null;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<WishModel> createWish(@RequestBody WishModel model) {

        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult deleteWish(@PathVariable("id") BigInteger id) {

        return null;
    }
}
