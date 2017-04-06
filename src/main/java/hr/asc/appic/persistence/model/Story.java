	package hr.asc.appic.persistence.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Document
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class Story implements Comparable<Story> {

    @Id
    @Setter(value = AccessLevel.NONE)
    private String id;
    @DBRef
    private Wish wish;
    @DBRef
    private User creator;
    @DBRef
    private User sponsor;

    private Date created;
    private String description;

    /**
     * Represents picture URL's
     */
    private List<String> pictures = new LinkedList<>();

    private Integer reportCount;

    @Override
    public int compareTo(Story story) {
        return created.compareTo(story.created);
    }
}
