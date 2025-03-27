package lk.viraj.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class  UserDTO {
    private UUID uid;
    private String googleId;
    private boolean isGoogleUser;
    @Email(message = "Email should be valid")
    private String email;
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password should contain at least one digit, one lowercase letter, one uppercase letter, one special character and should be 8 characters long")
    private String password;
    private String imagePath;
    @Pattern(regexp = "^[A-za-z ]+$", message = "Name should only contain letters and spaces")
    @Size(min = 4, max = 30, message = "Name should contain 4 to 30 characters")
    private String name;
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number should contain 10 digits")
    private String contact;
    private String address;
    private String nic;
    private String role;
}
