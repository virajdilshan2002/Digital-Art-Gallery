package lk.viraj.backend.repo;

import lk.viraj.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByName(String name);
}
