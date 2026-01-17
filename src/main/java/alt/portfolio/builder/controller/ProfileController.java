package alt.portfolio.builder.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import alt.portfolio.builder.entity.Profile;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.services.ProfileServices;

@Controller
@RequestMapping("/profiles")
public class ProfileController {

	private final ProfileServices profileServices;

	public ProfileController(ProfileServices profileServices) {
		this.profileServices = profileServices;
	}

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

	@GetMapping("/create")
	public String createForm() {
		return "profiles/profileForm";
	}

	@PostMapping("/create")
	public String createProfile(Authentication authentication, @RequestParam("name") String name,
			@RequestParam("description") String description) {
		User user = (User) authentication.getPrincipal();
		profileServices.createProfile(user, name, description);
		return "redirect:/profiles";
	}

	@GetMapping("/{id}")
	public String showProfile(@PathVariable UUID id, Model model) {

		Profile profile = profileServices.findById(id);

		model.addAttribute("profile", profile);
		return "profiles/profileShow";
	}

	@PostMapping("/{id}/delete")
	public String deleteProfile(@PathVariable UUID id, Authentication authentication) {
		User user = (User) authentication.getPrincipal();
		profileServices.deleteProfile(id, user.getId());
		return "redirect:/profiles";
	}

	@GetMapping("/{id}/edit")
	public String editProfileForm(@PathVariable UUID id, Model model) {
		Profile profile = profileServices.findById(id);
		model.addAttribute("profile", profile);
		// The template file is named `profileEdit.html` under `templates/profiles/`.
		// Return the matching view name so the template engine can find it.
		return "profiles/profileEdit";
	}

	@PostMapping("/{id}/edit")
	public String updateProfile(@PathVariable UUID id, @RequestParam("name") String name,
			@RequestParam("description") String description, Authentication authentication) {

		User user = (User) authentication.getPrincipal();
		profileServices.editProfile(id, name, description, user.getId());

		return "redirect:/profiles/" + id;
	}

}