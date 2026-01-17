package alt.portfolio.builder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuration MVC pour gérer les vues simples sans controller
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

	// Permet d'associer directement une URL à une vue sans passer par un controller
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// racine → dashboard
		registry.addViewController("/").setViewName("dashboard/index");

		// Redirige l'URL "/login" vers la vue "/users/formLogin"
		registry.addViewController("/login").setViewName("/users/formLogin");
	}
}
