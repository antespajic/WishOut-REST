package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class OfferModel {

    private BigInteger id;
    private BigInteger userId;
    private BigInteger wishId;
    private String description;
    private Date created;
    private Long upvoteCount;
    private Long reportCount;
}
