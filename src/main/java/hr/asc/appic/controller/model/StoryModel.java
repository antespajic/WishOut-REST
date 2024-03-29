package hr.asc.appic.controller.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
public class StoryModel {

    private String id;
    private String wishId;
    private String creatorId;
    private String sponsorId;
    private String description;
    private Date created;
    private List<String> pictures = new LinkedList<>();
    private Integer reportCount;
}
