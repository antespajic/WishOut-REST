package hr.asc.appic.persistence.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of={"id"})
public class Wish implements IPost {

	@Id
	private BigInteger id;
	
	@DBRef
	private User user;
	private String title;
	private String description;
	private List<String> categories;
	private Date createdAt;

	@DBRef
	private Offer offer;
	private Integer state;
	
	private Long upvoteCount;
	private Long reportCount;

}
