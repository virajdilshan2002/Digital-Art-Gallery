package lk.viraj.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "art")
public class Art {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    private String name;
    private String description;
    private String imagePath;
    private BigDecimal price;
    @ManyToOne
    private Category category;
    @ManyToOne
    private User user;

    public Art() {
    }

    public Art(UUID uid, String name, String desc, String imagePath, BigDecimal price, Category category, User user) {
        this.uid = uid;
        this.name = name;
        this.description = desc;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
