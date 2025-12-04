package alt.portfolio.builder.dtos;

import alt.portfolio.builder.entity.User;
import lombok.Data;

//Le DTO sert a selectionner les champ qui nous interresse uniquement parmis tout ceux creer dans le formulaire + recuperer les membres
@Data
public class UserRequestDto {
	private String firstname;
	private String lastname;
	private String username;
	private String email;
	
	// méthode qui permet d'affecter les membres récupéré dans le form a un User
	public User toUser(User user) {
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setUsername(username);
		user.setEmail(email);
		return user;
		
	}
}
