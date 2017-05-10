package hr.asc.appic.persistence.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
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
public class Wish implements Comparable<Wish> {

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

    @Override
    public int compareTo(Wish wish) {
        int result = created.compareTo(wish.created);
        if (result == 0) {
            result = upvoteCount.compareTo(wish.upvoteCount);
        }
        return result;
    }
}
