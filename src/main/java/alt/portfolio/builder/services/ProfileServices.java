package alt.portfolio.builder.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.Template;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.repository.ProfileRepository;
import alt.portfolio.builder.repository.TemplateRepository;

@Service
public class ProfileServices {

	@Autowired
	private ProfileRepository profileRepository;

	// Repository pour retrouver un template PDF par son identifiant
	@Autowired
	private TemplateRepository templateRepository;

	public List<Profile> findByOwner(User owner) {
		return profileRepository.findByOwner(owner);
	}

	public Profile createProfile(User owner, String name, String description) {
		Profile profile = new Profile();
		profile.setOwner(owner);
		profile.setName(name);
		profile.setDescription(description);
		return profileRepository.save(profile);
	}

	public Profile findById(UUID id) {
		return profileRepository.findById(id).orElseThrow(() -> new RuntimeException("Profile not found"));
	}

	/* ================= DELETE ================= */

	public void deleteProfile(UUID profileId, UUID ownerId) {
		Profile profile = findById(profileId);

		if (!profile.getOwner().getId().equals(ownerId)) {
			throw new RuntimeException("Forbidden");
		}

		profileRepository.delete(profile);
	}

	/* ================= TEMPLATE ================= */

	// Met à jour uniquement le template PDF du profil (appelé depuis PdfController lors de l'export)
	// templateId = null → remet le template par défaut (classique)
	public void updateTemplate(UUID profileId, UUID templateId) {
		Profile profile = findById(profileId);
		if (templateId != null) {
			Template template = templateRepository.findById(templateId).orElse(null);
			profile.setTemplate(template);
		} else {
			profile.setTemplate(null);
		}
		profileRepository.save(profile);
	}

	/* ================= EDIT ================= */

	// Met à jour le nom, la description et le template PDF du profil
	// templateId peut être null si l'utilisateur ne veut pas de template spécifique (utilise le classique par défaut)
	public Profile editProfile(UUID profileId, String name, String description, UUID ownerId, UUID templateId) {
		Profile profile = findById(profileId);

		// Vérifie que le profil appartient bien à l'utilisateur connecté
		if (!profile.getOwner().getId().equals(ownerId)) {
			throw new RuntimeException("Forbidden");
		}

		profile.setName(name);
		profile.setDescription(description);

		// Mise à jour du template PDF (null = classique par défaut à l'export)
		if (templateId != null) {
			Template template = templateRepository.findById(templateId).orElse(null);
			profile.setTemplate(template);
		} else {
			profile.setTemplate(null);
		}

		return profileRepository.save(profile);
	}

}
