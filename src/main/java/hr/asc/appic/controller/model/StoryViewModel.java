package hr.asc.appic.controller.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class StoryViewModel {

    private String wishId;
    private String creatorId;
    private String sponsorId;

    private Date created;
    private String description;

    /**
     * Represents picture URL's
     */
    private List<String> pictures = new LinkedList<>();

    private String reportCount;
}
