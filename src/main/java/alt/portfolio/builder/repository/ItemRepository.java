package alt.portfolio.builder.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alt.portfolio.builder.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, UUID> {

}
