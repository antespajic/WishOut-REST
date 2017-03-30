package hr.asc.appic.persistence.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;

@Document
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class Offer {

    @Id
    private BigInteger id;
    private BigInteger userId;
    private BigInteger wishId;
    private String description;
    private Date created;
    private Long upvoteCount;
    private Long reportCount;
}
