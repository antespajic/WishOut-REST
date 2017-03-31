package hr.asc.appic.persistence.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
public class User {

    @Id private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String gender;
    private Date dateOfBirth;
    private Date dateOfRegistration;
    private String profilePicture;
    private String contactNumber;
    private String contactFacebook;
    private Boolean profileConfirmed;
    private String coins;

    private Set<String> upvotes = new HashSet<>();
    private Set<String> reports = new HashSet<>();

    private Set<Wish> wishes = new HashSet<>();
    private Set<Offer> offers = new HashSet<>();
    private Set<Story> stories = new HashSet<>();
}
