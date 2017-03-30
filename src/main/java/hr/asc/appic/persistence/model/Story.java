package hr.asc.appic.persistence.model;

import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Story {

    @Id
    @Setter(value = AccessLevel.NONE)
    private BigInteger id;
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
    private Set<String> pictures = new HashSet<>();

    private Long reportCount;
}
