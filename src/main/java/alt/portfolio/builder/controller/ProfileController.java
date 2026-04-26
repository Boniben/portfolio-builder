package alt.portfolio.builder.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.Template;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.repository.TemplateRepository;
import alt.portfolio.builder.services.ProfileServices;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

	private final ProfileServices profileServices;

	// Repository pour récupérer la liste des templates disponibles
	@Autowired
	private TemplateRepository templateRepository;

	public ProfileController(ProfileServices profileServices) {
		this.profileServices = profileServices;
	}

	// Récupération de l'utilisateur connecté, récupération de ses profils via le
	// service,
	// si aucun profil -> redirige vers /profiles/create, sinon affiche la liste des
	// profils (profiles/profileIndex)
	@GetMapping
	public String index(Authentication authentication, Model model) {
		User user = (User) authentication.getPrincipal();
		List<Profile> profiles = profileServices.findByOwner(user);

		if (profiles.isEmpty()) {
			return "redirect:/profiles/create";
		}

		model.addAttribute("profiles", profiles);
		return "profiles/profileIndex";
	}

	// Affiche le formulaire de création de profil (profiles/profileForm)
	@GetMapping("/create")
	public String createForm() {
		return "profiles/profileForm";
	}

	// Récupération de l'utilisateur connecté + récupération des champs du
	// formulaire (name + description),
	// création du profil, puis redirection vers /profiles
	@PostMapping("/create")
	public String createProfile(Authentication authentication, @RequestParam("name") String name,
			@RequestParam("description") String description) {
		User user = (User) authentication.getPrincipal();
		profileServices.createProfile(user, name, description);
		return "redirect:/profiles";
	}

	// Récupération de l'id depuis l'URL (/profiles/{id}),
	// récupération du profil correspondant via le service + liste des templates disponibles,
	// puis affichage du détail (profiles/profileShow)
	@GetMapping("/{id}")
	public String showProfile(@PathVariable UUID id, Model model) {

		Profile profile = profileServices.findById(id);
		List<Template> templates = templateRepository.findAll();

		model.addAttribute("profile", profile);
		// Liste des templates pour les boutons radio de sélection du template PDF
		model.addAttribute("templates", templates);
		// Id du template actuellement sélectionné (pour pré-cocher le bon bouton radio en JS)
		model.addAttribute("currentTemplateId",
				profile.getTemplate() != null ? profile.getTemplate().getId().toString() : "");

		return "profiles/profileShow";
	}

	// Récupération de l'id depuis l'URL (/profiles/{id}/delete) + récupération de
	// l'utilisateur connecté,
	// suppression du profil si le profil appartient à l'utilisateur, puis
	// redirection vers /profiles
	@PostMapping("/{id}/delete")
	public String deleteProfile(@PathVariable UUID id, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		profileServices.deleteProfile(id, user.getId());
		return "redirect:/profiles";
	}

	// Récupération de l'id depuis l'URL (/profiles/{id}/edit),
	// récupération du profil correspondant + liste des templates disponibles,
	// pré-remplissage du formulaire, puis affichage de la vue (profiles/profileEdit)
	@GetMapping("/{id}/edit")
	public String editProfileForm(@PathVariable UUID id, Model model) {
		Profile profile = profileServices.findById(id);
		List<Template> templates = templateRepository.findAll();

		model.addAttribute("profile", profile);
		// Liste des templates injectée dans la vue pour la liste déroulante
		model.addAttribute("templates", templates);
		return "profiles/profileEdit";
	}

	// Récupération de l'id depuis l'URL (/profiles/{id}/edit) + récupération des
	// champs modifiés (name, description, templateId optionnel)
	// + récupération de l'utilisateur connecté,
	// mise à jour du profil si le profil appartient à l'utilisateur, puis
	// redirection vers /profiles/{id}
	@PostMapping("/{id}/edit")
	public String updateProfile(@PathVariable UUID id,
			@RequestParam("name") String name,
			@RequestParam("description") String description,
			@RequestParam(value = "templateId", required = false) UUID templateId,
			Authentication authentication) {

		User user = (User) authentication.getPrincipal();
		// templateId peut être null si "Aucun (classique par défaut)" est sélectionné
		profileServices.editProfile(id, name, description, user.getId(), templateId);

		return "redirect:/profiles/" + id;
	}

}
