package hr.asc.appic.controller;

import hr.asc.appic.service.ContentOrigin;
import hr.asc.appic.service.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@RestController
@RequestMapping("/upvote")
public class UpvoteController {

    @Autowired
    private UpvoteService upvoteService;

    @RequestMapping(value = "/wish", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity> upvoteWish(@RequestParam("wish") BigInteger wishId,
                                                     @RequestParam("user") BigInteger userId) {
        return upvoteService.upvote(ContentOrigin.WISH, wishId, userId);
    }

    @RequestMapping(value = "/offer", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity> upvoteOffer(@RequestParam("offer") BigInteger offerId,
                                                      @RequestParam("user") BigInteger userId) {
        return upvoteService.upvote(ContentOrigin.OFFER, offerId, userId);
    }
}
