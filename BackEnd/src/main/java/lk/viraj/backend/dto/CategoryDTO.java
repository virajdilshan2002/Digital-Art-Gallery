package lk.viraj.backend.dto;

import java.util.UUID;

public class CategoryDTO {
    private UUID cid;
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(UUID cid, String name) {
        this.cid = cid;
        this.name = name;
    }

    public UUID getCid() {
        return cid;
    }

    public void setCid(UUID cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "cid=" + cid +
                ", name='" + name + '\'' +
                '}';
    }
}
