package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class WishModel {

    private BigInteger id;
    private BigInteger userId;
    private String title;
    private String description;
    private List<String> categories;
    private Set<String> pictures;
    private Date created;
    private BigInteger offerId;
    private Integer state;
    private Long upvoteCount;
    private Long reportCount;
}
