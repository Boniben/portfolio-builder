package alt.portfolio.builder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import alt.portfolio.builder.entity.Category;
import alt.portfolio.builder.entity.Template;
import alt.portfolio.builder.repository.CategoryRepository;
import alt.portfolio.builder.repository.TemplateRepository;

@SpringBootApplication
public class PortfolioBuilderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PortfolioBuilderApplication.class, args);
    }

    // Initialise les catégories de base au démarrage si la table est vide
    @Bean
    public CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                String[][] categories = {
                    { "Formation",   "true",  "false" },
                    { "Expérience",  "true",  "false" },
                    { "Compétences", "false", "false" },
                    { "Projets",     "false", "true"  },
                    { "Autre",       "false", "false" }
                };
                for (String[] cat : categories) {
                    Category category = new Category();
                    category.setName(cat[0]);
                    category.setHasDates(Boolean.parseBoolean(cat[1]));
                    category.setHasLink(Boolean.parseBoolean(cat[2]));
                    categoryRepository.save(category);
                }
                System.out.println("✅ Catégories insérées.");
            }
        };
    }

    // Initialise les templates PDF au démarrage
    // Vérifie template par template pour ne pas dupliquer les existants
    @Bean
    public CommandLineRunner initTemplates(TemplateRepository templateRepository) {
        return args -> {

            // Tableau : { nom, description, nomFichier }
            String[][] templates = {
                { "Classique",      "Mise en page sobre et professionnelle, noir et blanc.",     "classic"  },
                { "Moderne",        "Design coloré avec une barre latérale verte.",              "modern"   },
                { "Minimaliste",    "Ultra simple, juste l'essentiel, très épuré.",              "minimal"  },
                { "Fun",            "Coloré et dynamique, pour se démarquer.",                   "fun"      },
                { "Informatique",   "Style terminal sombre, parfait pour les profils IT.",        "tech"     },
                { "Manuel",         "Mise en page document technique, structuré et numéroté.",   "manual"   }
            };

            // Insère uniquement les templates qui n'existent pas encore en base
            int inserted = 0;
            for (String[] t : templates) {
                if (!templateRepository.existsByFilename(t[2])) {
                    Template template = new Template();
                    template.setName(t[0]);
                    template.setDescription(t[1]);
                    template.setFilename(t[2]);
                    templateRepository.save(template);
                    inserted++;
                }
            }

            if (inserted > 0) {
                System.out.println("✅ " + inserted + " template(s) PDF insérés.");
            }
        };
    }
}
