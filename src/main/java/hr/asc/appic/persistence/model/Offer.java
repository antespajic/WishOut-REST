package hr.asc.appic.persistence.model;

import java.math.BigInteger;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offer implements IPost {

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
