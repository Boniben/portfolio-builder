package alt.portfolio.builder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)

				.authorizeHttpRequests(req -> req

						// ✅ Public
						.requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/"),
								PathPatternRequestMatcher.withDefaults().matcher("/login"),
								PathPatternRequestMatcher.withDefaults().matcher("/css/**"),
								PathPatternRequestMatcher.withDefaults().matcher("/js/**"),
								PathPatternRequestMatcher.withDefaults().matcher("/img/**"),
								PathPatternRequestMatcher.withDefaults().matcher("/users/create/**"),
								PathPatternRequestMatcher.withDefaults().matcher("/users/register/**"))
						.permitAll()

						// ✅ ADMIN seulement
						.requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/users/**")).hasRole("ADMIN")

						// ✅ USER + ADMIN
						.requestMatchers(PathPatternRequestMatcher.withDefaults().matcher("/profiles/**"))
						.hasAnyRole("USER", "ADMIN")

						// ✅ le reste : connecté
						.anyRequest().authenticated())

				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true).permitAll())

				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/").invalidateHttpSession(true)
						.deleteCookies("JSESSIONID").permitAll());

		return http.build();
	}

	@Bean
	PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider(UserDetailsService userService) {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userService);
		auth.setPasswordEncoder(getPasswordEncoder());
		return auth;
	}
}
