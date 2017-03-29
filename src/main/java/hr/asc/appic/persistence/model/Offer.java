package hr.asc.appic.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class Offer {

    @Id
    private BigInteger id;
    @DBRef
    private User user;
    @DBRef
    private Wish wish;
    private String description;
    private Long upvoteCount;
    private Long reportCount;
}
