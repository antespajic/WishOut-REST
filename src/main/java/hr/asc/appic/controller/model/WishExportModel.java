package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class WishExportModel {

    private UserLightViewModel creator;
    private WishModel wish;
    private InteractionModel interaction;
    private OfferExportModel myOffer;
    private List<OfferExportModel> offers;
}
