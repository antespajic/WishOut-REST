package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class OfferExportModel {

    private UserLightViewModel user;
    private OfferModel offer;
    private InteractionModel interaction;
}
