package hr.asc.appic.controller;

import hr.asc.appic.service.UpvoteService;
import hr.asc.appic.service.utility.ContentOrigin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/upvote")
public class UpvoteController {

    @Autowired
    private UpvoteService upvoteService;

    @RequestMapping(value = "/wish", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> upvoteWish(@RequestParam("wish") String wishId,
                                                        @RequestParam("user") String userId) {
        return upvoteService.upvote(ContentOrigin.WISH, wishId, userId);
    }

    @RequestMapping(value = "/offer", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> upvoteOffer(@RequestParam("offer") String offerId,
                                                         @RequestParam("user") String userId) {
        return upvoteService.upvote(ContentOrigin.OFFER, offerId, userId);
    }
}
