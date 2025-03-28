package lk.viraj.backend.dto.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemFormDataDTO {
    private String name;
    private MultipartFile image;
    private String description;
    private String categoryName;
    private BigDecimal price;
    private int qty;
}
