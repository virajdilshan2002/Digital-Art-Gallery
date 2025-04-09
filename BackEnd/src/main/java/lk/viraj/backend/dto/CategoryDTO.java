package lk.viraj.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private int cid;
    private String name;
    private String description;
}
