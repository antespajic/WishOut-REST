package hr.asc.appic.controller.model;

import java.util.ArrayList;
import java.util.Date;
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
public class WishModel {

    private String id;
    private String userId;
    private String title;
    private String description;
    private List<String> categories = new ArrayList<>();
    private List<String> pictures = new ArrayList<>();
    private Date created;
    private Integer state;
    private Integer upvoteCount;
    private Integer reportCount;
    private Long timeLeft;
}
