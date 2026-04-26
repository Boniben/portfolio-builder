package alt.portfolio.builder.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

// Entité représentant un modèle de mise en page pour l'export PDF du CV
@Entity
@Getter
@Setter
public class Template {

    @Id
    private UUID id = UUID.randomUUID();

    // Nom du template affiché à l'utilisateur (ex: "Classique", "Moderne")
    @Column(length = 50, nullable = false)
    private String name;

    // Description courte du style du template
    @Column(length = 200)
    private String description;

    // Nom du fichier HTML dans src/main/resources/pdf-templates/ (sans extension)
    // Ex: "classic" → chargera "pdf-templates/classic.html"
    @Column(length = 50, nullable = false)
    private String filename;
}
