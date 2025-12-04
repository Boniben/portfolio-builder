package alt.portfolio.builder.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

//on creer la correspondance avec la base

@Entity
@Getter @Setter
@Table(name = "user")
public class User {
	@Id
	private UUID id=UUID.randomUUID();
	
	@Column(length = 45, nullable = false)
	private String firstname="";
	
	@Column(length = 45, nullable = false)
	private String lastname;
	
	@Column(length = 45, nullable = false, unique = true)
	private String username;
	
	@Column(length = 150, nullable = false, unique = true)
	private String email;
	
	@Column(length = 255, nullable = true)
	private String password;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Profile> profiles;
	
	//ajouter un profile méthode pour 
	public void addProfile(Profile profile) {
		this.profiles.add(profile);
		profile.setOwner(this);
	}
	
	
	
	
	
	
		
}
