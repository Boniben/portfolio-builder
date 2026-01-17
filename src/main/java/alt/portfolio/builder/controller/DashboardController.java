package alt.portfolio.builder.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

	@GetMapping("/")
	public String dashboard(Authentication auth, Model model) {

		boolean isAuthenticated = auth != null && auth.isAuthenticated()
				&& !(auth instanceof AnonymousAuthenticationToken);

		model.addAttribute("isAuthenticated", isAuthenticated);

		boolean isAdmin = false;
		boolean isUser = false;

		if (isAuthenticated) {
			isAdmin = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

			isUser = auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
		}

		model.addAttribute("isAdmin", isAdmin);
		model.addAttribute("isUser", isUser);

		return "dashboard/index";
	}
}
