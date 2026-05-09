package alt.portfolio.builder.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import alt.portfolio.builder.dtos.UserRequestDto;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.services.DbUserService;
import alt.portfolio.builder.services.UserServices;

// Contrôleur du panel d'administration — toutes ces routes sont réservées aux ADMIN (voir SecurityConfig)
@Controller
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserServices userService;

    @Autowired
    private DbUserService dbUserService;

    /* ================= LISTE ================= */

    // Affiche la liste de tous les utilisateurs inscrits
    @GetMapping(path = { "", "/" })
    public ModelAndView index() {
        return new ModelAndView("users/index", "users", userService.getUsers());
    }

    /* ================= DÉTAIL ================= */

    // Affiche la fiche détaillée d'un utilisateur (infos + ses profils)
    @GetMapping("/show/{id}")
    public String show(@PathVariable UUID id, ModelMap model) {
        User user = userService.findById(id);
        model.addAttribute("showUser", user);
        return "users/show";
    }

    /* ================= CRÉATION (admin crée un compte) ================= */

    // Affiche le formulaire de création d'un nouvel utilisateur
    @GetMapping("/create")
    public String create(ModelMap model) {
        model.addAttribute("user", new User());
        return "users/Userform";
    }

    // Enregistre le nouvel utilisateur créé par l'admin
    @PostMapping("/create")
    public RedirectView createUser(@ModelAttribute UserRequestDto createdUser) {
        userService.createUser(createdUser);
        return new RedirectView("/users");
    }

    /* ================= MODIFICATION ================= */

    // Affiche le formulaire de modification d'un utilisateur (admin)
    // L'admin peut changer prénom, nom et email — pas le mot de passe
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable UUID id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("editUser", user);
        return "users/adminEdit";
    }

    // Enregistre les modifications apportées par l'admin à un utilisateur
    // L'admin peut aussi réinitialiser le mot de passe sans connaître l'ancien
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable UUID id,
            @RequestParam("firstname") String firstname,
            @RequestParam("lastname") String lastname,
            @RequestParam("email") String email,
            @RequestParam(value = "newPassword", required = false) String newPassword,
            Model model) {

        // Mise à jour des infos de base (address et phone gérés par l'utilisateur lui-même)
        userService.updateUser(id, firstname, lastname, email, null, null);

        // Réinitialisation du mot de passe si le champ est rempli
        if (newPassword != null && !newPassword.isBlank()) {

            // Validation : minimum 6 caractères
            if (newPassword.length() < 6) {
                User editUser = userService.findById(id);
                model.addAttribute("editUser", editUser);
                model.addAttribute("error", "Le mot de passe doit faire au moins 6 caractères.");
                return "users/adminEdit";
            }

            // Encodage et sauvegarde du nouveau mot de passe
            userService.changePassword(id, newPassword);
        }

        return "redirect:/users/show/" + id;
    }

    /* ================= SUPPRESSION ================= */

    // Supprime un seul utilisateur depuis sa fiche détail
    @PostMapping("/{id}/delete")
    public String deleteOne(@PathVariable UUID id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    // Supprime plusieurs utilisateurs sélectionnés via les cases à cocher de la liste
    @PostMapping("/delete")
    public String deleteUsers(@RequestParam(value = "selected", required = false) List<UUID> ids) {
        if (ids != null) {
            ids.forEach(userService::deleteById);
        }
        return "redirect:/users";
    }

    /* ================= RÔLE ================= */

    // Bascule le rôle d'un utilisateur entre USER et ADMIN
    // Si USER → devient ADMIN, si ADMIN → redevient USER
    @PostMapping("/{id}/toggleRole")
    public String toggleRole(@PathVariable UUID id) {
        userService.toggleRole(id);
        return "redirect:/users/show/" + id;
    }
}
