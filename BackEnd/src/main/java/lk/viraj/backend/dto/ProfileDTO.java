package lk.viraj.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProfileDTO {
    private UUID uid;
    private String email;
    private String name;
    private String contact;
    private String address;
}
