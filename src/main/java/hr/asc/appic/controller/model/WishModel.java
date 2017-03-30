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
public class WishModel {

    private String id;
    private String userId;
    private String title;
    private String description;
    private List<String> categories = new LinkedList<>();
    private List<String> pictures = new LinkedList<>();
    private Date created;
    private String offerId;
    private Integer state;
    private String upvoteCount;
    private String reportCount;
}
