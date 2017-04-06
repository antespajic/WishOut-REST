package hr.asc.appic.mapping;

import hr.asc.appic.controller.model.OfferExportModel;
import hr.asc.appic.controller.model.OfferModel;
import hr.asc.appic.persistence.model.Offer;
import hr.asc.appic.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OfferMapper implements Mapper<Offer, OfferModel> {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Offer modelToPojo(OfferModel model) {
        return new Offer()
                .setId(model.getId())
                .setUserId(model.getUserId())
                .setWishId(model.getWishId())
                .setDescription(model.getDescription())
                .setCreated(model.getCreated())
                .setChosen(model.getChosen())
                .setUpvoteCount(model.getUpvoteCount())
                .setReportCount(model.getReportCount());
    }

    @Override
    public OfferModel pojoToModel(Offer offer) {
        return new OfferModel()
                .setId(offer.getId())
                .setUserId(offer.getUserId())
                .setWishId(offer.getWishId())
                .setDescription(offer.getDescription())
                .setCreated(offer.getCreated())
                .setChosen(offer.getChosen())
                .setUpvoteCount(offer.getUpvoteCount())
                .setReportCount(offer.getReportCount());
    }

    @Override
    public void updatePojoFromModel(Offer offer, OfferModel model) {
        if (model.getDescription() != null) {
            offer.setDescription(model.getDescription());
        }
    }

    public OfferExportModel exportModelForUser(Offer offer, User user) {
        return new OfferExportModel()
                .setUser(userMapper.lightModelFromUser(user))
                .setOffer(pojoToModel(offer));
        // In the future, interaction needs to be implemented.
    }
}
