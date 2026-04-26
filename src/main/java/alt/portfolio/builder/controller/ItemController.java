package alt.portfolio.builder.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import alt.portfolio.builder.entity.Item;
import alt.portfolio.builder.entity.Rubric;
import alt.portfolio.builder.services.ItemService;
import alt.portfolio.builder.services.RubricService;

// Contrôleur qui gère les routes liées aux éléments d'une rubrique
// Un élément est un contenu concret dans une rubrique (ex: "BTS SIO" dans "Formation")
@Controller
public class ItemController {

    // Injection des services nécessaires
    @Autowired
    private ItemService itemService;

    @Autowired
    private RubricService rubricService;

    /* ================= CRÉER ================= */

    // Affiche le formulaire pour ajouter un élément à une rubrique
    // URL : GET /rubrics/{rubricId}/items/create
    @GetMapping("/rubrics/{rubricId}/items/create")
    public String createForm(@PathVariable UUID rubricId, Model model) {
        Rubric rubric = rubricService.findById(rubricId);

        model.addAttribute("rubric", rubric);
        // On passe l'id du profil pour le bouton "Annuler"
        model.addAttribute("profileId", rubric.getProfile().getId());
        return "items/itemForm";
    }

    // Enregistre le nouvel élément en base de données
    // URL : POST /rubrics/{rubricId}/items/create
    @PostMapping("/rubrics/{rubricId}/items/create")
    public String createItem(@PathVariable UUID rubricId,
                             @RequestParam("title") String title,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate) {
        Rubric rubric = rubricService.findById(rubricId);

        // Création de l'élément via le service
        itemService.create(title, description, startDate, endDate, rubric);

        // Redirection vers le profil après création
        return "redirect:/profiles/" + rubric.getProfile().getId();
    }

    /* ================= MODIFIER ================= */

    // Affiche le formulaire de modification d'un élément existant
    // URL : GET /items/{id}/edit
    @GetMapping("/items/{id}/edit")
    public String editForm(@PathVariable UUID id, Model model) {
        Item item = itemService.findById(id);

        model.addAttribute("item", item);
        // On passe l'id du profil pour le bouton "Annuler"
        model.addAttribute("profileId", item.getRubric().getProfile().getId());
        return "items/itemEdit";
    }

    // Enregistre les modifications de l'élément
    // URL : POST /items/{id}/edit
    @PostMapping("/items/{id}/edit")
    public String updateItem(@PathVariable UUID id,
                             @RequestParam("title") String title,
                             @RequestParam(value = "description", required = false) String description,
                             @RequestParam(value = "startDate", required = false) String startDate,
                             @RequestParam(value = "endDate", required = false) String endDate) {
        // On récupère l'id du profil AVANT la mise à jour pour la redirection
        Item item = itemService.findById(id);
        UUID profileId = item.getRubric().getProfile().getId();

        itemService.update(id, title, description, startDate, endDate);

        // Redirection vers le profil après modification
        return "redirect:/profiles/" + profileId;
    }

    /* ================= SUPPRIMER ================= */

    // Supprime un élément et redirige vers le profil
    // URL : POST /items/{id}/delete
    @PostMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable UUID id) {
        // On récupère l'id du profil AVANT la suppression pour la redirection
        Item item = itemService.findById(id);
        UUID profileId = item.getRubric().getProfile().getId();

        itemService.delete(id);

        return "redirect:/profiles/" + profileId;
    }

    /* ================= RÉORDONNER ================= */

    // Reçoit la nouvelle liste ordonnée d'IDs d'éléments depuis le frontend (JavaScript)
    // URL : POST /rubrics/{rubricId}/items/reorder
    // Le corps de la requête est une chaîne d'IDs séparés par des virgules
    @PostMapping("/rubrics/{rubricId}/items/reorder")
    @ResponseBody
    public ResponseEntity<String> reorderItems(@PathVariable UUID rubricId,
                                                @RequestBody String body) {
        // Conversion de la chaîne "id1,id2,id3" en liste de UUID
        List<UUID> orderedIds = Arrays.stream(body.split(","))
                .map(String::trim)
                .map(UUID::fromString)
                .collect(Collectors.toList());

        itemService.reorder(orderedIds);

        // Retourne 200 OK au JavaScript qui a fait la requête
        return ResponseEntity.ok("OK");
    }

}
