package alt.portfolio.builder.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import alt.portfolio.builder.entity.User;
import alt.portfolio.builder.repository.UserRepository;

// Service gérant les utilisateurs pour Spring Security
@Service
public class DbUserService implements UserDetailsService {
 
    // Repository pour accéder aux données utilisateur
    @Autowired
    private UserRepository uRepo;
 
    // Encodeur de mot de passe
    @Autowired
    private PasswordEncoder pEncoder;
 
    // Méthode requise par UserDetailsService pour récupérer un utilisateur par username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = uRepo.findByUsername(username);
        return optUser.orElseThrow(() -> new UsernameNotFoundException("Utilisateur inconnu"));
    }
 
    // Encode le mot de passe d'un utilisateur et le met à jour dans l'objet
    public void encodePassword(User user) {
        user.setPassword(pEncoder.encode(user.getPassword()));
    }
    
    // Crée un nouvel utilisateur avec username et password
    // Met firstname, lastname, email à la valeur du username pour simplifier
    // Encode le mot de passe et sauvegarde en base
    public User createUser(String username, String password) {
    	User user = new User();
    	user.setFirstname(username);
    	user.setLastname(username);
    	user.setEmail(username);
    	user.setUsername(username);
    	user.setPassword(password);
    	encodePassword(user);
        return uRepo.save(user);
    }
}
