package alt.portfolio.builder.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alt.portfolio.builder.entity.User;

// Repository JPA pour gérer les utilisateurs
// Permet de manipuler les données et de faire le lien avec la base de données
// C'est à partir du repository que l'on effectue les requêtes
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

	// Recherche un utilisateur par son username

	public Optional<User> findByUsername(String username);

}