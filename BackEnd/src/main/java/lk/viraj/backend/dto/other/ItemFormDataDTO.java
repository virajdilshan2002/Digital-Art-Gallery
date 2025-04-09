package lk.viraj.backend.dto.other;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemFormDataDTO {
    private UUID iid;
    private String name;
    private MultipartFile itemImage;
    private String description;
    private String categoryId;
    private BigDecimal price;
    private int qty;
}
