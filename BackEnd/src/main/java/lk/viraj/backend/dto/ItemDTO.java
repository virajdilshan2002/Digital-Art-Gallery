package lk.viraj.backend.dto;

import lk.viraj.backend.entity.Category;
import lk.viraj.backend.entity.User;
import java.math.BigDecimal;
import java.util.UUID;

public class ItemDTO {
    private UUID uid;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private Category category;
    private User user;

    public ItemDTO() {
    }

    public ItemDTO(UUID uid, String name, String description, String image, BigDecimal price, Category category, User user) {
        this.uid = uid;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.category = category;
        this.user = user;
    }

    public UUID getUid() {
        return uid;
    }

    public void setUid(UUID uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
