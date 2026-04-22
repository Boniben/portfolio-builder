package alt.portfolio.builder.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import alt.portfolio.builder.entity.Category;
import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.Rubric;
import alt.portfolio.builder.repository.CategoryRepository;
import alt.portfolio.builder.services.ProfileServices;
import alt.portfolio.builder.services.RubricService;

// Contrôleur qui gère les routes liées aux rubriques
// Une rubrique appartient à un profil (ex: Formation, Expérience...)
@Controller
public class RubricController {

    // Injection des services nécessaires
    @Autowired
    private RubricService rubricService;

    @Autowired
    private ProfileServices profileServices;

    // Injection du repository des catégories (pour peupler la liste déroulante)
    @Autowired
    private CategoryRepository categoryRepository;

    /* ================= CRÉER ================= */

    // Affiche le formulaire pour ajouter une rubrique à un profil
    // URL : GET /profiles/{profileId}/rubrics/create
    @GetMapping("/profiles/{profileId}/rubrics/create")
    public String createForm(@PathVariable UUID profileId, Model model) {
        // Récupération du profil auquel on ajoute la rubrique
        Profile profile = profileServices.findById(profileId);

        // Récupération de toutes les catégories pour la liste déroulante
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("profile", profile);
        model.addAttribute("categories", categories);
        return "rubrics/rubricForm";
    }

    // Enregistre la nouvelle rubrique en base de données
    // URL : POST /profiles/{profileId}/rubrics/create
    @PostMapping("/profiles/{profileId}/rubrics/create")
    public String createRubric(@PathVariable UUID profileId,
                               @RequestParam("name") String name,
                               @RequestParam("categoryId") UUID categoryId) {
        // Récupération du profil et de la catégorie choisie
        Profile profile = profileServices.findById(profileId);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        // Création de la rubrique via le service
        rubricService.create(name, category, profile);

        // Redirection vers la page du profil après création
        return "redirect:/profiles/" + profileId;
    }

    /* ================= MODIFIER ================= */

    // Affiche le formulaire de modification d'une rubrique
    // URL : GET /rubrics/{id}/edit
    @GetMapping("/rubrics/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        Rubric rubric = rubricService.findById(id);
        List<Category> categories = categoryRepository.findAll();

        model.addAttribute("rubric", rubric);
        model.addAttribute("categories", categories);
        // On passe l'id du profil pour le bouton "Annuler"
        model.addAttribute("profileId", rubric.getProfile().getId());
        return "rubrics/rubricEdit";
    }

    // Enregistre les modifications de la rubrique
    // URL : POST /rubrics/{id}/edit
    @PostMapping("/rubrics/{id}/edit")
    public String updateRubric(@PathVariable UUID id,
                               @RequestParam("name") String name,
                               @RequestParam("categoryId") UUID categoryId) {
        // On récupère l'id du profil AVANT la mise à jour pour la redirection
        Rubric rubric = rubricService.findById(id);
        UUID profileId = rubric.getProfile().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Catégorie introuvable"));

        rubricService.update(id, name, category);

        // Redirection vers la page du profil après modification
        return "redirect:/profiles/" + profileId;
    }

    /* ================= SUPPRIMER ================= */

    // Supprime une rubrique et redirige vers le profil
    // URL : POST /rubrics/{id}/delete
    @PostMapping("/rubrics/{id}/delete")
    public String deleteRubric(@PathVariable UUID id) {
        // On récupère l'id du profil AVANT la suppression pour la redirection
        Rubric rubric = rubricService.findById(id);
        UUID profileId = rubric.getProfile().getId();

        rubricService.delete(id);

        return "redirect:/profiles/" + profileId;
    }

}
