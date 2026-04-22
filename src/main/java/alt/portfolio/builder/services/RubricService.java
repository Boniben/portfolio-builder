package alt.portfolio.builder.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.entity.Category;
import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.Rubric;
import alt.portfolio.builder.repository.RubricRepository;

// Service qui contient la logique métier pour les rubriques
// C'est ici qu'on effectue les opérations : créer, lire, modifier, supprimer
@Service
public class RubricService {

    // Injection du repository pour accéder à la base de données
    @Autowired
    private RubricRepository rubricRepository;

    /* ================= LISTE ================= */

    // Retourne toutes les rubriques d'un profil donné
    public List<Rubric> findByProfile(Profile profile) {
        return rubricRepository.findByProfile(profile);
    }

    /* ================= CRÉER ================= */

    // Crée une nouvelle rubrique et la lie à un profil
    // L'ordre est calculé automatiquement (nombre de rubriques existantes + 1)
    public Rubric create(String name, Category category, Profile profile) {
        Rubric rubric = new Rubric();
        rubric.setName(name);
        rubric.setCategory(category);
        rubric.setProfile(profile);

        // Calcul automatique de l'ordre d'affichage
        int order = rubricRepository.findByProfile(profile).size() + 1;
        rubric.setOrder_(order);

        return rubricRepository.save(rubric);
    }

    /* ================= LIRE ================= */

    // Récupère une rubrique par son identifiant unique (UUID)
    // Lance une exception si elle n'existe pas
    public Rubric findById(UUID id) {
        return rubricRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rubrique introuvable"));
    }

    /* ================= MODIFIER ================= */

    // Met à jour le nom et la catégorie d'une rubrique existante
    public Rubric update(UUID id, String name, Category category) {
        Rubric rubric = findById(id);
        rubric.setName(name);
        rubric.setCategory(category);
        return rubricRepository.save(rubric);
    }

    /* ================= SUPPRIMER ================= */

    // Supprime une rubrique par son identifiant
    public void delete(UUID id) {
        rubricRepository.deleteById(id);
    }

}
