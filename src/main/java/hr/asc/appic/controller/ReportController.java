package hr.asc.appic.controller;

import hr.asc.appic.service.ContentOrigin;
import hr.asc.appic.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.math.BigInteger;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/wish", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity> reportWish(@RequestParam("wish") BigInteger wishId,
                                                     @RequestParam("user") BigInteger userId) {
        return reportService.report(ContentOrigin.WISH, wishId, userId);
    }

    @RequestMapping(value = "/offer", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity> reportOffer(@RequestParam("offer") BigInteger offerId,
                                                      @RequestParam("user") BigInteger userId) {
        return reportService.report(ContentOrigin.OFFER, offerId, userId);
    }

    @RequestMapping(value = "/story", method = RequestMethod.PUT)
    public DeferredResult<ResponseEntity> reportStory(@RequestParam("story") BigInteger storyId,
                                                      @RequestParam("user") BigInteger userId) {
        return reportService.report(ContentOrigin.STORY, storyId, userId);
    }
}