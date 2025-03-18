package lk.viraj.backend.dto.other;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ItemFormDataDTO {
    private String name;
    private MultipartFile image;
    private String description;
    private double price;
    private int qty;

    public ItemFormDataDTO() {
    }

    public ItemFormDataDTO(String name, MultipartFile image, String description, double price, int qty) {
        this.name = name;
        this.image = image;
        this.description = description;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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
                ", price=" + price +
                ", qty=" + qty +
                '}';
    }
}
