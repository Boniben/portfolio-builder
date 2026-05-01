package alt.portfolio.builder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.services.DbUserService;
import alt.portfolio.builder.services.UserServices;
import jakarta.servlet.http.HttpSession;

// Contrôleur pour la gestion du compte de l'utilisateur connecté
// Différent de UserController (réservé ADMIN) : ici chaque user gère SON propre compte
@Controller
@RequestMapping("/account")
public class AccountController {

    // Injection du service utilisateur pour les opérations CRUD
    @Autowired
    private UserServices userServices;

    // Injection du service d'authentification pour recharger l'utilisateur depuis la base
    @Autowired
    private DbUserService dbUserService;

    /* ================= MODIFIER SES INFOS ================= */

    // Affiche le formulaire de modification des informations personnelles
    // URL : GET /account/edit
    // Le paramètre "success" est optionnel : présent après un enregistrement réussi
    @GetMapping("/edit")
    public String editForm(Authentication authentication, Model model,
                           @RequestParam(value = "success", required = false) String success) {
        // On recharge l'utilisateur depuis la base pour avoir les données à jour
        // (le principal Spring Security peut contenir des données en cache)
        User currentUser = (User) dbUserService.loadUserByUsername(authentication.getName());
        model.addAttribute("user", currentUser);

        // Si le paramètre "success" est présent dans l'URL, on affiche le message de confirmation
        if (success != null) {
            model.addAttribute("success", true);
        }
        return "account/editForm";
    }

    // Enregistre les modifications des informations personnelles
    // URL : POST /account/edit
    @PostMapping("/edit")
    public String updateAccount(Authentication authentication,
                                @RequestParam("firstname") String firstname,
                                @RequestParam("lastname") String lastname,
                                @RequestParam("email") String email,
                                @RequestParam(value = "address", required = false) String address,
                                @RequestParam(value = "phone", required = false) String phone,
                                @RequestParam(value = "currentPassword", required = false) String currentPassword,
                                @RequestParam(value = "newPassword", required = false) String newPassword,
                                Model model) {
        // Récupération de l'utilisateur connecté (données fraîches depuis la base)
        User currentUser = (User) dbUserService.loadUserByUsername(authentication.getName());

        // ===== VALIDATION DES CHAMPS =====

        // Vérification que les champs obligatoires ne sont pas vides
        if (firstname.isBlank() || lastname.isBlank() || email.isBlank()) {
            model.addAttribute("user", currentUser);
            model.addAttribute("error", "Prénom, nom et email sont obligatoires.");
            return "account/editForm";
        }

        // Vérification du format de l'email (doit contenir @ et un point)
        if (!email.contains("@") || !email.contains(".")) {
            model.addAttribute("user", currentUser);
            model.addAttribute("error", "L'adresse email n'est pas valide.");
            return "account/editForm";
        }

        // Vérification que l'email n'est pas déjà utilisé par un autre compte
        if (userServices.emailExistsForOtherUser(email, currentUser.getId())) {
            model.addAttribute("user", currentUser);
            model.addAttribute("error", "Cette adresse email est déjà utilisée par un autre compte.");
            return "account/editForm";
        }

        // Vérification de la longueur du nouveau mot de passe si l'utilisateur veut en changer
        if (newPassword != null && !newPassword.isBlank() && newPassword.length() < 6) {
            model.addAttribute("user", currentUser);
            model.addAttribute("error", "Le nouveau mot de passe doit faire au moins 6 caractères.");
            return "account/editForm";
        }

        // ===== FIN VALIDATION =====

        // Mise à jour des infos personnelles (toujours effectuée)
        // address et phone peuvent être vides → on passe null si la chaîne est vide
        userServices.updateUser(currentUser.getId(), firstname, lastname, email,
                (address != null && !address.isBlank()) ? address : null,
                (phone != null && !phone.isBlank()) ? phone : null);

        // Changement de mot de passe uniquement si les champs sont remplis
        if (newPassword != null && !newPassword.isEmpty()) {

            // Vérification que l'ancien mot de passe est correct avant de changer
            if (!userServices.checkPassword(currentUser, currentPassword)) {
                // Mot de passe actuel incorrect → erreur, on réaffiche le formulaire
                model.addAttribute("user", currentUser);
                model.addAttribute("error", "Mot de passe actuel incorrect.");
                return "account/editForm";
            }

            // Ancien mot de passe correct → on change pour le nouveau
            userServices.changePassword(currentUser.getId(), newPassword);
        }

        // Redirection vers le formulaire avec un message de succès
        return "redirect:/account/edit?success";
    }

    /* ================= SUPPRIMER SON COMPTE ================= */

    // Affiche la page de confirmation de suppression du compte
    // URL : GET /account/delete
    @GetMapping("/delete")
    public String deleteForm() {
        return "account/deleteConfirm";
    }

    // Supprime le compte si le mot de passe saisi est correct
    // URL : POST /account/delete
    @PostMapping("/delete")
    public String deleteAccount(Authentication authentication,
                                @RequestParam("password") String password,
                                HttpSession session,
                                Model model) {
        // Récupération de l'utilisateur connecté (données fraîches depuis la base)
        User currentUser = (User) dbUserService.loadUserByUsername(authentication.getName());

        // Vérification du mot de passe avant suppression
        if (!userServices.checkPassword(currentUser, password)) {
            // Mot de passe incorrect → on réaffiche le formulaire avec un message d'erreur
            model.addAttribute("error", "Mot de passe incorrect. Veuillez réessayer.");
            return "account/deleteConfirm";
        }

        // Invalidation de la session AVANT la suppression (déconnexion)
        session.invalidate();

        // Suppression du compte en base (cascade → supprime aussi les profils)
        userServices.deleteById(currentUser.getId());

        // Redirection vers la page d'accueil
        return "redirect:/";
    }
}
