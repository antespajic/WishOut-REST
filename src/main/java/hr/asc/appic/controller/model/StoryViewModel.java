package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class StoryViewModel {

    private BigInteger wishId;
    private BigInteger creatorId;
    private BigInteger sponsorId;

    private Date created;
    private String description;

    /**
     * Represents picture URL's
     */
    private List<String> pictures = new LinkedList<>();

    private Long reportCount;
}
