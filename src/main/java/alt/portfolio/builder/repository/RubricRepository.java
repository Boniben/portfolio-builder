package alt.portfolio.builder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.Rubric;

// Interface repository pour les rubriques
// Spring Data JPA génère automatiquement les requêtes SQL de base (findById, save, deleteById...)
@Repository
public interface RubricRepository extends JpaRepository<Rubric, UUID> {

    // Récupère toutes les rubriques associées à un profil donné
    List<Rubric> findByProfile(Profile profile);

}
