package hr.asc.appic.controller;

import hr.asc.appic.controller.model.WishExportModel;
import hr.asc.appic.controller.model.WishModel;
import hr.asc.appic.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/wish")
public class WishController {

    @Autowired
    private WishService wishService;

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<WishModel>> createWish(@RequestBody WishModel model) {
        return wishService.createWish(model);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<WishExportModel>> getWish(@PathVariable("id") String id,
                                                                   @RequestParam("index") Integer index,
                                                                   @RequestParam("size") Integer size) {
        return wishService.getWish(id, index, size);
    }

    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public DeferredResult<ResponseEntity<?>> updateWish(@PathVariable("id") String id,
                                                     @RequestBody WishModel model) {
        return wishService.updateWish(id, model);
    }

    @RequestMapping(
            value = "/{wishId}/{offerId}",
            method = RequestMethod.PUT
    )
    public DeferredResult<ResponseEntity<?>> assignOffer(@PathVariable("wishId") String wishId,
                                                      @PathVariable("offerId") String offerId,
                                                      @RequestParam("confirmed") boolean confirmed) {
        return wishService.updateOffer(wishId, offerId, confirmed);
    }
}
