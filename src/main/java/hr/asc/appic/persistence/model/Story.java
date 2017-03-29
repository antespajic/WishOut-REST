package hr.asc.appic.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class Story {

    @Id
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
