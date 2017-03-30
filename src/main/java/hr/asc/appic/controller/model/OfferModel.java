package hr.asc.appic.controller.model;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class OfferModel {

    private String id;
    private String userId;
    private String wishId;
    private String description;
    private Date created;
    private String upvoteCount;
    private String reportCount;
}
