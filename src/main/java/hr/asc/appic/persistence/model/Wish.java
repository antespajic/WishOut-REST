package hr.asc.appic.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class Wish {

    @Id
    private String id;
    @DBRef
    private User user;
    private String title;
    private String description;
    private List<String> categories = new ArrayList<>();
    private List<String> pictures = new ArrayList<>();
    private Date created;
    private Integer state;
    private Integer upvoteCount;
    private Integer reportCount;

    @DBRef
    private Set<Offer> offers = new HashSet<>();
}
