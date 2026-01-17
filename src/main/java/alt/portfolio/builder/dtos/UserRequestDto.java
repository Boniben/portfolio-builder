package alt.portfolio.builder.dtos;

import alt.portfolio.builder.entity.User;
import lombok.Data;

@Data
public class UserRequestDto {

	private String firstname;
	private String lastname;
	private String username;
	private String email;
	private String password; // ← OBLIGATOIRE

	public User toUser(User user) {
		user.setFirstname(firstname);
		user.setLastname(lastname);
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password); // ← TRANSFERT
		return user;
	}
}
