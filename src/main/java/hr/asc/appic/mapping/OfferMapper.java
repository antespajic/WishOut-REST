package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.persistence.model.Offer;
import org.springframework.stereotype.Service;

@Service
public class OfferMapper implements Mapper<Offer, OfferModel> {

    @Override
    public Offer modelToPojo(OfferModel model) {
        Offer offer = new Offer();
        offer.setId(model.getId())
                .setDescription(model.getDescription())
                .setUpvoteCount(model.getUpvoteCount())
                .setReportCount(model.getReportCount());
        return offer;
    }

    @Override
    public OfferModel pojoToModel(Offer offer) {
        OfferModel model = new OfferModel();
        model.setId(offer.getId())
                .setUserId(offer.getUser().getId())
                .setWishId(offer.getWish().getId())
                .setDescription(offer.getDescription())
                .setUpvoteCount(offer.getUpvoteCount())
                .setReportCount(offer.getReportCount());
        return model;
    }
}
