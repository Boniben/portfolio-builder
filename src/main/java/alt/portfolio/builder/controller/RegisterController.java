package alt.portfolio.builder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import alt.portfolio.builder.dtos.UserRequestDto;
import alt.portfolio.builder.services.UserServices;

// Contrôleur public pour l'inscription d'un nouvel utilisateur
// Séparé de UserController (réservé aux admins) pour éviter tout conflit de sécurité
// Route /register → accessible sans connexion (voir SecurityConfig)
@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private UserServices userServices;

    // Affiche le formulaire d'inscription
    @GetMapping
    public String showForm() {
        return "register/registerForm";
    }

    // Enregistre le nouvel utilisateur après validation des données saisies
    @PostMapping
    public String register(@ModelAttribute UserRequestDto dto, Model model) {

        // ===== VALIDATION DES CHAMPS =====

        // Vérification que tous les champs obligatoires sont remplis
        if (dto.getFirstname().isBlank() || dto.getLastname().isBlank()
                || dto.getUsername().isBlank() || dto.getEmail().isBlank()
                || dto.getPassword().isBlank()) {
            model.addAttribute("error", "Tous les champs sont obligatoires.");
            return "register/registerForm";
        }

        // Vérification du format de l'email
        if (!dto.getEmail().contains("@") || !dto.getEmail().contains(".")) {
            model.addAttribute("error", "L'adresse email n'est pas valide.");
            return "register/registerForm";
        }

        // Mot de passe : minimum 6 caractères
        if (dto.getPassword().length() < 6) {
            model.addAttribute("error", "Le mot de passe doit faire au moins 6 caractères.");
            return "register/registerForm";
        }

        // Vérification que le login n'est pas déjà pris
        if (userServices.usernameExists(dto.getUsername())) {
            model.addAttribute("error", "Ce nom d'utilisateur est déjà pris. Choisissez-en un autre.");
            return "register/registerForm";
        }

        // Vérification que l'email n'est pas déjà utilisé
        if (userServices.emailExists(dto.getEmail())) {
            model.addAttribute("error", "Cette adresse email est déjà associée à un compte.");
            return "register/registerForm";
        }

        // ===== FIN VALIDATION =====

        // Tout est valide → on crée le compte et on redirige vers la page de connexion
        userServices.createUser(dto);
        return "redirect:/login";
    }
}
