package alt.portfolio.builder.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import alt.portfolio.builder.entity.Item;
import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.Rubric;

// Service responsable de la génération du CV en PDF
// Charge un template HTML, le remplit avec les données du profil, puis le convertit en PDF
@Service
public class PdfService {

    // Point d'entrée principal : génère le PDF pour un profil donné
    // Retourne un tableau d'octets prêt à envoyer en réponse HTTP
    public byte[] generatePdf(Profile profile) throws Exception {

        // Détermine le nom du fichier template à utiliser
        // Si aucun template n'est sélectionné sur le profil, on utilise "classic" par défaut
        String filename = "classic";
        if (profile.getTemplate() != null) {
            filename = profile.getTemplate().getFilename();
        }

        // Charge le contenu HTML du template depuis src/main/resources/pdf-templates/
        String htmlTemplate = loadTemplate(filename);

        // Injecte les données du profil dans le template HTML
        String htmlContent = buildHtml(htmlTemplate, profile);

        // Convertit le HTML enrichi en PDF et retourne les octets
        return convertToPdf(htmlContent);
    }

    // Charge le fichier HTML du template depuis le classpath (resources/pdf-templates/)
    private String loadTemplate(String filename) throws IOException {
        ClassPathResource resource = new ClassPathResource("pdf-templates/" + filename + ".html");
        try (InputStream inputStream = resource.getInputStream()) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    // Remplace les marqueurs {{ownerName}}, {{description}}, {{rubrics}} dans le template
    // par les vraies données du profil
    private String buildHtml(String template, Profile profile) {

        // Nom complet de l'utilisateur propriétaire du profil (affiché comme titre du CV)
        String ownerName = profile.getOwner().getFirstname() + " " + profile.getOwner().getLastname();

        // Construit le bloc HTML des rubriques (seulement celles qui sont visibles)
        String rubricsHtml = buildRubricsHtml(profile);

        // Remplacement des marqueurs dans le template
        return template
            .replace("{{ownerName}}", escapeHtml(ownerName))
            .replace("{{description}}", escapeHtml(profile.getDescription()))
            .replace("{{rubrics}}", rubricsHtml); // déjà du HTML, pas d'échappement ici
    }

    // Construit le HTML de toutes les rubriques visibles et leurs éléments
    private String buildRubricsHtml(Profile profile) {
        StringBuilder html = new StringBuilder();

        // Si le profil n'a pas de rubriques, on retourne une chaîne vide
        if (profile.getRubrics() == null || profile.getRubrics().isEmpty()) {
            return "";
        }

        for (Rubric rubric : profile.getRubrics()) {
            // On ignore les rubriques masquées (visible = false)
            if (!rubric.isVisible()) {
                continue;
            }

            // Titre de la rubrique (ex: Formation, Expérience...)
            html.append("<div class=\"rubric\">");
            html.append("<h3>").append(escapeHtml(rubric.getName())).append("</h3>");

            // Éléments de la rubrique (ex: BTS SIO au lycée X)
            if (rubric.getItems() != null) {
                for (Item item : rubric.getItems()) {
                    html.append("<div class=\"item\">");

                    // Titre de l'élément (en gras)
                    html.append("<strong>").append(escapeHtml(item.getTitle())).append("</strong>");

                    // Dates (affichées seulement si elles sont renseignées)
                    if (item.getStartDate() != null) {
                        html.append("<span class=\"dates\"> | ").append(item.getStartDate());
                        if (item.getEndDate() != null) {
                            html.append(" – ").append(item.getEndDate());
                        }
                        html.append("</span>");
                    }

                    // Description de l'élément
                    if (item.getDescription() != null && !item.getDescription().isBlank()) {
                        html.append("<p>").append(escapeHtml(item.getDescription())).append("</p>");
                    }

                    html.append("</div>"); // fin .item
                }
            }

            html.append("</div>"); // fin .rubric
        }

        return html.toString();
    }

    // Échappe les caractères spéciaux HTML pour éviter les injections dans le PDF
    // (& < > " sont remplacés par leurs entités HTML)
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;");
    }

    // Convertit une chaîne HTML en PDF en utilisant la librairie openhtmltopdf
    // Retourne le PDF sous forme de tableau d'octets (byte[])
    private byte[] convertToPdf(String htmlContent) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();

            // Mode rapide : désactive certaines vérifications pour une génération plus rapide
            builder.useFastMode();

            // Fournit le contenu HTML à convertir
            // Le second paramètre (baseUri) est null car on n'a pas de ressources externes
            builder.withHtmlContent(htmlContent, null);

            // Définit le flux de sortie (mémoire)
            builder.toStream(outputStream);

            // Lance la conversion
            builder.run();

            return outputStream.toByteArray();
        }
    }
}
