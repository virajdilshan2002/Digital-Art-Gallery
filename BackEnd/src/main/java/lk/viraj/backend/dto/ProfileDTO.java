package lk.viraj.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProfileDTO {
    private String uid;
    private String email;
    private String name;
    private String contact;
    private String address;
    private String role;
    private String imagePath;
}
