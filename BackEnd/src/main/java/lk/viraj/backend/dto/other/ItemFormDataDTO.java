package lk.viraj.backend.dto.other;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Component
public class ItemFormDataDTO {
    private String name;
    private MultipartFile image;
    private String description;
    private String categoryName;
    private BigDecimal price;
    private int qty;

    public ItemFormDataDTO() {
    }

    public ItemFormDataDTO(String name, MultipartFile image, String description, String categoryName, BigDecimal price, int qty) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.categoryName = categoryName;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    @Override
    public String toString() {
        return "ItemFormDataDTO{" +
                "name='" + name + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
