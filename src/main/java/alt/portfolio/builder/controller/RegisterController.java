package alt.portfolio.builder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    // Enregistre le nouvel utilisateur et redirige vers la page de connexion
    @PostMapping
    public String register(@ModelAttribute UserRequestDto dto) {
        userServices.createUser(dto);
        return "redirect:/login";
    }
}
