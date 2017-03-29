package hr.asc.appic.controller;

import hr.asc.appic.controller.model.OfferModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public DeferredResult<OfferModel> createOffer(@RequestBody OfferModel model) {

        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult deleteOffer(@PathVariable("id") BigInteger id) {

        return null;
    }
}
