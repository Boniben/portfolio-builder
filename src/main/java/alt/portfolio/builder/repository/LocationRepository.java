package alt.portfolio.builder.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alt.portfolio.builder.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {

}
