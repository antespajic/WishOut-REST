package hr.asc.appic.controller.model;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * This class is a model used for transferring data to view
 * 
 * @author antes
 *
 */
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain=true)
public class UserViewModel {

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
	private Boolean profileConfirmed;
	private Long coins;

	private Set<BigInteger> upvotes = new HashSet<>();
}
