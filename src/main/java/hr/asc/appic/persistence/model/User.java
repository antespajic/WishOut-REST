package hr.asc.appic.persistence.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	private BigInteger id;
	
	private String email;
	private String password;
	private String name;
	private String surname;
	private String country;
	private String city;
	private String gender;
	private Date dateOfBirth;
	private Date dateOfRegistration;
	private String profilePicture;
	private String contactNumber;
	private String contactFacebook;
	private boolean profileConfirmed;
	private Long coins;

	private Set<BigInteger> upvotes = new HashSet<>();
	
}
