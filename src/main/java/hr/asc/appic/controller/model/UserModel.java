package hr.asc.appic.controller.model;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Accessors(chain = true)
public class UserModel {

    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String city;
    private String country;
    private String gender;
    private Date dateOfBirth;
    private Date dateOfRegistration;
    private String profilePicture;
    private String contactNumber;
    private String contactFacebook;
    private Boolean profileConfirmed;
    private String coins;
}
