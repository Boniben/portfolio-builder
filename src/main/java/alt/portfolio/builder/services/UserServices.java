package alt.portfolio.builder.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.dtos.UserRequestDto;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.repository.UserRepository;

// Classe service contenant la logique métier liée aux utilisateurs
@Service
public class UserServices {

	// Injection du repository pour accéder à la base de données
	// Seul le service communique directement avec le repository
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// Récupère la liste de tous les utilisateurs
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	// Crée un nouvel utilisateur à partir des données du DTO
	// Convertit le DTO en entité User puis sauvegarde en base
	public User createUser(UserRequestDto userRequest) {

		User user = userRequest.toUser(new User());

		// 🔐 encodage du mot de passe ICI
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	// Recherche un utilisateur par son identifiant
	// Lance une exception si l'utilisateur n'existe pas
	public User findById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("utilisateur introuvable" + id));
	}

	// Supprime un utilisateur en base à partir de son identifiant
	public void deleteById(UUID id) {
		userRepository.deleteById(id);
	}
}
