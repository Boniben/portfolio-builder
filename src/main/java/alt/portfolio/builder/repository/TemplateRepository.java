package alt.portfolio.builder.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import alt.portfolio.builder.entity.Template;

// Repository pour accéder aux templates en base de données
public interface TemplateRepository extends JpaRepository<Template, UUID> {

    // Vérifie si un template avec ce nom de fichier existe déjà en base
    // Utilisé au démarrage pour ne pas insérer les templates déjà présents
    boolean existsByFilename(String filename);
}
