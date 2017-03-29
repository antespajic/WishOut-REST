package hr.asc.appic.persistence.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@Document
@NoArgsConstructor
public class User {

    @Id
    @Setter(value = AccessLevel.NONE)
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
    private Set<BigInteger> reports = new HashSet<>();

    private Set<Wish> wishes = new HashSet<>();
    private Set<Offer> offers = new HashSet<>();
    private Set<Story> stories = new HashSet<>();
}
