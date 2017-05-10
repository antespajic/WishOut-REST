package hr.asc.appic.controller.model;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class OfferModel {

    private String id;
    private String userId;
    private String wishId;
    private String description;
    private Date created;
    private Boolean chosen;
    private Integer upvoteCount;
    private Integer reportCount;
}
