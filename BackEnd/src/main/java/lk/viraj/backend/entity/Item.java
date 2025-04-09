package lk.viraj.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID iid;
    private String name;
    private String description;
    private String image;
    private BigDecimal price;
    private int qty;

    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
}
