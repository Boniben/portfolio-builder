package alt.portfolio.builder.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alt.portfolio.builder.entity.User;

//sert a manipuler les donnée et fait le lien avec la base de donnée c'est a partir du repo que l'on fait les requetes

@Repository
public interface UserRepository extends JpaRepository<User,UUID>{
	
	public Optional<User> findByUsername(String username);
	
}
