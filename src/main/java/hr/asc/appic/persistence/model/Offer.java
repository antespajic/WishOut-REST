package hr.asc.appic.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class Offer implements Comparable<Offer> {

    @Id
    private String id;
    private String userId;
    private String wishId;
    private String description;
    private Date created;
    private Boolean chosen;
    private Integer upvoteCount;
    private Integer reportCount;

    @Override
    public int compareTo(Offer offer) {
        int result = created.compareTo(offer.created);
        if (result == 0) {
            result = upvoteCount.compareTo(offer.upvoteCount);
        }
        return result;
    }
}
