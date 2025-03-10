package lk.viraj.backend.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "category")
public class Category {
    @Id
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Art> arts;

    public Category() {
    }

    public Category(String name, List<Art> arts) {
        this.name = name;
        this.arts = arts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Art> getArts() {
        return arts;
    }

    public void setArts(List<Art> arts) {
        this.arts = arts;
    }

}
