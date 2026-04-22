package alt.portfolio.builder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import alt.portfolio.builder.entity.Category;
import alt.portfolio.builder.repository.CategoryRepository;

@SpringBootApplication
public class PortfolioBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioBuilderApplication.class, args);
    }

    // CommandLineRunner : code exécuté automatiquement au démarrage de l'application
    // Ici, on insère les catégories de base si la table est vide
    @Bean
    public CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {

            // On vérifie que la table est vide pour ne pas créer des doublons
            if (categoryRepository.count() == 0) {

                // Tableau : { nom, hasDates, hasLink }
                // hasDates = true si la rubrique a des dates (ex: Formation, Expérience)
                // hasLink  = true si la rubrique peut avoir un lien (ex: Projets)
                String[][] categories = {
                    { "Formation",   "true",  "false" },
                    { "Expérience",  "true",  "false" },
                    { "Compétences", "false", "false" },
                    { "Projets",     "false", "true"  },
                    { "Autre",       "false", "false" }
                };

                // Création et sauvegarde de chaque catégorie en base de données
                for (String[] cat : categories) {
                    Category category = new Category();
                    category.setName(cat[0]);
                    category.setHasDates(Boolean.parseBoolean(cat[1]));
                    category.setHasLink(Boolean.parseBoolean(cat[2]));
                    categoryRepository.save(category);
                }

                System.out.println("✅ Catégories de base insérées en base de données.");
            }
        };
    }

}
