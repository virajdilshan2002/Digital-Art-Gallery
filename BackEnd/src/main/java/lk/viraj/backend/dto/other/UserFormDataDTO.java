package lk.viraj.backend.dto.other;

import org.springframework.web.multipart.MultipartFile;

public class UserFormDataDTO {
    private MultipartFile image;
    private String email;
    private String password;
    private String name;
    private String contact;
    private String address;
    private String nic;
    private String role;
}
