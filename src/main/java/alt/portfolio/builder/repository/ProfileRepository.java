package alt.portfolio.builder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.User;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, UUID> {

	List<Profile> findByOwner(User owner);

	public Profile findProfileById(UUID id);

	public void deleteProfileById(UUID id);

}
