package alt.portfolio.builder.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

//

@Controller
@RequestMapping("users")
public class UserController {

	// ici on donne acces au controller aux différents services
	@Autowired
	private UserServices userService;

	@Autowired
	private DbUserService dbUserService;

	// on recupere les info de user service on les passe a users et on les passe
	// dans l'index
	@GetMapping(path = { "", "/" })
	public ModelAndView index() {
		return new ModelAndView("/users/index", "users", userService.getUsers());
	}

	// sert a recupérer des données depuis le serveur
	@GetMapping("/create")
	public String create(ModelMap model) {
		model.addAttribute("user", new User());
		return "/users/userForm";
	}

	// sert a creer ou envoyer des données sur le serveur
	@PostMapping("/create")
	public RedirectView createUser(@ModelAttribute UserRequestDto createdUser) {
		userService.createUser(createdUser);
		return new RedirectView("/users");
	}

	@PostMapping("/delete")
	public String deleteUsers(@RequestParam(value = "selected", required = false) List<UUID> ids) {
		if (ids != null) {
			ids.forEach(userService::deleteById);
		}
		return "redirect:/users";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable UUID id, ModelMap model) {
		User user = userService.findById(id);
		model.addAttribute("showUser", user);
		return "/users/show";

	}

}
