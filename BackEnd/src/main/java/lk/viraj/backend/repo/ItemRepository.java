package lk.viraj.backend.repo;

import lk.viraj.backend.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface ItemRepository extends JpaRepository<Item, UUID> {
}
