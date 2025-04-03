package lk.viraj.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProfileDTO {
    private String uid;
    private String email;
    private String name;
    private String contact;
    private String address;
    private String role;
    private String imagePath;
}
