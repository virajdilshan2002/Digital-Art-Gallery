package lk.viraj.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid")
    private int cid;
    @Column(unique = true)
    private String name;
    private String description;
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Item> items;

}
