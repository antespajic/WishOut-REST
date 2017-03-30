package hr.asc.appic.persistence.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@Document
@NoArgsConstructor
public class User {

    @Id
    @Setter(value = AccessLevel.NONE)
    private String id;

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
    private String coins;

    private Set<String> upvotes = new HashSet<>();
    private Set<String> reports = new HashSet<>();

    @DBRef private Set<Wish> wishes = new HashSet<>();
    @DBRef private Set<Offer> offers = new HashSet<>();
    @DBRef private Set<Story> stories = new HashSet<>();
}
