package hr.asc.appic.persistence.model;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
    private List<String> categories = new LinkedList<>();
    private List<String> pictures = new LinkedList<>();
    private Date created;
    @DBRef
    private Offer offer;
    private Set<Offer> offers = new HashSet<>();
    private Integer state;
    private String upvoteCount;
    private String reportCount;
}
