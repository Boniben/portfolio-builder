package alt.portfolio.builder.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.dtos.UserRequestDto;
import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.repository.UserRepository;


@Service
public class UserServices {
	
	//seule le service a acces au repo, et le service en a besoin car le services manipule les données c'est ici que l'on fait les requetes
	@Autowired
	private UserRepository userRepository;
	
	public List<User> getUsers(){
		return userRepository.findAll();
	}
	

	//On recupere les données du DTO et on les sauvegarde dans le repo
	public User createUser(UserRequestDto userRequest) {
		User user=userRequest.toUser(new User());
		return userRepository.save(user);
	}

	public User findById(UUID id) {
		return userRepository.findById(id).orElseThrow(() -> new RuntimeException("utilisateur introuvable"+id));
	}
	
	public void deleteById(UUID id) {
		userRepository.deleteById(id);
		}
}
