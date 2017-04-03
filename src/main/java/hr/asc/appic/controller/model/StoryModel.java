package hr.asc.appic.controller.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class StoryModel {

    private String id;
    private String wishId;
    private String creatorId;
    private String sponsorId;

    private Date created;
    private String description;

    /**
     * Represents picture URL's
     */
    private List<String> pictures = new LinkedList<>();

    private Integer reportCount;
}
