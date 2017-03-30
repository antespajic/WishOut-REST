package hr.asc.appic.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserLightViewModel {

    private BigInteger id;
    private String firstName;
    private String lastName;
    private String profilePicture;
}
