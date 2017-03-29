package hr.asc.appic.controller.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain=true)
public class StoryViewModel {

	private BigInteger wishId;
	private BigInteger creatorId;
	private BigInteger sponsorId;
	
	private Date created;
	private String description;
	
	/**
	 * Represents picture URL's
	 */
	private Set<String> pictures = new HashSet<>();
	
	private Long reportCount;
}
