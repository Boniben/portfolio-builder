package alt.portfolio.builder.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

// ControllerAdvice : s'exécute avant CHAQUE requête sur tous les controllers
// Injecte les infos d'authentification dans tous les templates Mustache
@ControllerAdvice
public class AuthControllerAdvice {

    // Retourne true si l'utilisateur est connecté (pas anonyme)
    // Disponible dans tous les templates via {{#isAuthenticated}} ... {{/isAuthenticated}}
    @ModelAttribute("isAuthenticated")
    public boolean isAuthenticated() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth != null
                    && auth.isAuthenticated()
                    && !auth.getPrincipal().equals("anonymousUser");
        } catch (Exception e) {
            return false;
        }
    }

    // Retourne true si l'utilisateur a le rôle ADMIN
    // Disponible dans tous les templates via {{#isAdmin}} ... {{/isAdmin}}
    @ModelAttribute("isAdmin")
    public boolean isAdmin() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) return false;
            return auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        } catch (Exception e) {
            return false;
        }
    }
}
