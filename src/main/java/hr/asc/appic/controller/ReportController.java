package hr.asc.appic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import hr.asc.appic.service.ReportService;
import hr.asc.appic.service.utility.ContentOrigin;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/wish", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> reportWish(@RequestParam("wish") String wishId,
                                                        @RequestParam("user") String userId) {
        return reportService.report(ContentOrigin.WISH, wishId, userId);
    }

    @RequestMapping(value = "/offer", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> reportOffer(@RequestParam("offer") String offerId,
                                                         @RequestParam("user") String userId) {
        return reportService.report(ContentOrigin.OFFER, offerId, userId);
    }

    @RequestMapping(value = "/story", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity<?>> reportStory(@RequestParam("story") String storyId,
                                                         @RequestParam("user") String userId) {
        return reportService.report(ContentOrigin.STORY, storyId, userId);
    }
}
