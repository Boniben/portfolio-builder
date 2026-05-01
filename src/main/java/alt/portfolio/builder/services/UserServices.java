package alt.portfolio.builder.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.dtos.UserRequestDto;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.exeptions.EntityNotFoundException;
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
	public void deleteById(UUID id) throws EntityNotFoundException {
		if (userRepository.findById(id).isEmpty()) {
			throw new EntityNotFoundException("Utilisateur introuvable avec l'id : " + id);
		}
		userRepository.deleteById(id);
	}

	// Met à jour les informations personnelles d'un utilisateur
	// Le username n'est pas modifiable (identifiant de connexion)
	// address et phone peuvent être null si non renseignés
	public User updateUser(UUID id, String firstname, String lastname, String email, String address, String phone) {
		User user = findById(id);
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setEmail(email);
		user.setAddress(address);
		user.setPhone(phone);
		return userRepository.save(user);
	}

	// Vérifie que le mot de passe brut correspond au mot de passe encodé en base
	// Utilisé pour confirmer la suppression de compte et le changement de mot de passe
	public boolean checkPassword(User user, String rawPassword) {
		return passwordEncoder.matches(rawPassword, user.getPassword());
	}

	// Change le mot de passe d'un utilisateur après vérification de l'ancien
	// Encode le nouveau mot de passe avant de le sauvegarder
	public void changePassword(UUID id, String newPassword) {
		User user = findById(id);
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
	}

	// Bascule le rôle d'un utilisateur entre USER et ADMIN
	// Appelé depuis le panel admin pour promouvoir ou rétrograder un compte
	public void toggleRole(UUID id) {
		User user = findById(id);
		String newRole = user.getRole().equals("ADMIN") ? "USER" : "ADMIN";
		user.setRole(newRole);
		userRepository.save(user);
	}
}
