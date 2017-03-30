package hr.asc.appic.persistence.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
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
public class Offer {

    @Id
    private String id;
    private String userId;
    private String wishId;
    private String description;
    private Date created;
    private Boolean chosen;
    private String upvoteCount;
    private String reportCount;
}
