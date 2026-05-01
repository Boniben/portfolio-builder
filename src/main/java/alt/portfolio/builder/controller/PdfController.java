package alt.portfolio.builder.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.services.PdfService;
import alt.portfolio.builder.services.ProfileServices;

// Contrôleur gérant l'export PDF du CV
@Controller
public class PdfController {

    // Service qui récupère et met à jour le profil
    @Autowired
    private ProfileServices profileServices;

    // Service qui génère le PDF à partir du profil
    @Autowired
    private PdfService pdfService;

    // Route : GET /profiles/{id}/export/pdf?templateId=...&preview=false
    // templateId : optionnel — id du template à utiliser, sauvegardé sur le profil
    // preview    : optionnel — si true, ouvre le PDF dans le navigateur (inline)
    //                          si false (défaut), force le téléchargement (attachment)
    @GetMapping("/profiles/{id}/export/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @PathVariable UUID id,
            @RequestParam(required = false) String templateId,
            @RequestParam(required = false, defaultValue = "false") boolean preview) {
        try {
            // Convertit templateId (String) en UUID si présent et valide
            UUID templateUUID = null;
            if (templateId != null && !templateId.isBlank()) {
                try {
                    templateUUID = UUID.fromString(templateId);
                } catch (IllegalArgumentException e) {
                    // templateId invalide ou vide → on ignore, le défaut (classique) sera utilisé
                }
            }

            // Sauvegarde le template choisi sur le profil (mémorise le dernier choix)
            profileServices.updateTemplate(id, templateUUID);

            // Recharge le profil avec le template mis à jour
            Profile profile = profileServices.findById(id);

            // Génère le contenu PDF sous forme de tableau d'octets
            byte[] pdfBytes = pdfService.generatePdf(profile);

            // Construit un nom de fichier propre
            String filename = "cv-" + profile.getName().replaceAll("[^a-zA-Z0-9À-ÿ\\s]", "") + ".pdf";

            // "inline"     → le navigateur affiche le PDF dans un onglet (prévisualisation)
            // "attachment" → le navigateur propose le téléchargement du fichier
            String disposition = preview ? "inline" : "attachment";

            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition + "; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);

        } catch (Exception e) {
            // Log complet pour faciliter le débogage
            System.err.println("=== ERREUR GÉNÉRATION PDF ===");
            System.err.println("Message : " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
