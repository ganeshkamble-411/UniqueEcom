package Ecom.ModelDTO;

import lombok.Data;

@Data
public class UserSignInDetail {
    private Integer id;
    private String firstName;
    private String lastName;
    private String signinStatus;
    private String jwtToken; // ✅ Added for token
}