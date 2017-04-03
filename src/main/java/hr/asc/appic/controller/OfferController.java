package hr.asc.appic.controller;

import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity> createOffer(@RequestBody OfferModel model) {
        return offerService.createOffer(model);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public DeferredResult deleteOffer(@PathVariable("id") String id) {
        return offerService.deleteOffer(id);
    }
}
