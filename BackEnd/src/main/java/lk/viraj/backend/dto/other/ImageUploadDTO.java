package lk.viraj.backend.dto.other;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImageUploadDTO {
    private MultipartFile imageFile;
}
