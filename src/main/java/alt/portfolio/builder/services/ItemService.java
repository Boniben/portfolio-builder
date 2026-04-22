package alt.portfolio.builder.services;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.entity.Item;
import alt.portfolio.builder.entity.Rubric;
import alt.portfolio.builder.repository.ItemRepository;

// Service qui contient la logique métier pour les éléments (items)
// Un élément appartient à une rubrique (ex: "BTS SIO" dans la rubrique "Formation")
@Service
public class ItemService {

    // Injection du repository pour accéder à la base de données
    @Autowired
    private ItemRepository itemRepository;

    /* ================= CRÉER ================= */

    // Crée un nouvel élément dans une rubrique donnée
    // Les dates sont optionnelles : si le champ est vide, on met null
    public Item create(String title, String description, String startDate, String endDate, Rubric rubric) {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setRubric(rubric);

        // Conversion de la date de début (format "YYYY-MM-DD" ou vide)
        item.setStartDate(startDate != null && !startDate.isEmpty() ? LocalDate.parse(startDate) : null);

        // Conversion de la date de fin (format "YYYY-MM-DD" ou vide)
        item.setEndDate(endDate != null && !endDate.isEmpty() ? LocalDate.parse(endDate) : null);

        // Calcul automatique de l'ordre d'affichage dans la rubrique
        int order = rubric.getItems() != null ? rubric.getItems().size() + 1 : 1;
        item.setSortOrder(order);

        return itemRepository.save(item);
    }

    /* ================= LIRE ================= */

    // Récupère un élément par son identifiant unique (UUID)
    // Lance une exception si l'élément n'existe pas
    public Item findById(UUID id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élément introuvable"));
    }

    /* ================= MODIFIER ================= */

    // Met à jour les informations d'un élément existant
    public Item update(UUID id, String title, String description, String startDate, String endDate) {
        Item item = findById(id);
        item.setTitle(title);
        item.setDescription(description);

        // Mise à jour des dates (optionnelles)
        item.setStartDate(startDate != null && !startDate.isEmpty() ? LocalDate.parse(startDate) : null);
        item.setEndDate(endDate != null && !endDate.isEmpty() ? LocalDate.parse(endDate) : null);

        return itemRepository.save(item);
    }

    /* ================= SUPPRIMER ================= */

    // Supprime un élément par son identifiant
    public void delete(UUID id) {
        itemRepository.deleteById(id);
    }

}
