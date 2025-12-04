/*package alt.portfolio.builder.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Image {
	@Id
	private UUID id= UUID.randomUUID();
	
	@Column(length = 255, nullable = false)
	private String url;
	
	//A revoir
	
	@ManyToMany()
	private List<Profile> lesprofiles;
	
	
	
	

}
*/
