package lk.viraj.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ItemDTO {
    private UUID iid;
    private String name;
    private String image;
    private String description;
    private BigDecimal price;
    private int qty;
    private CategoryDTO category;
    private UserDTO user;

    public ItemDTO(String name, String image, String description, BigDecimal price, int qty, CategoryDTO category, UserDTO user) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.qty = qty;
        this.category = category;
        this.user = user;
    }
}
