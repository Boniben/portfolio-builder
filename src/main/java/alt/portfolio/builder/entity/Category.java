/*package alt.portfolio.builder.entity;


import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Category {
	@Id
	private UUID id= UUID.randomUUID();
	
	@Column(length = 50, nullable = false)
	private String name;
	
	//A revoir
	@Column(length = 5, nullable = false)
	private Boolean hasDates;
	
	//A revoir
	@Column(length = 5, nullable = false)
	private Boolean hasLink;
	
	//Arevoir
	@OneToMany()
	private List<Rubric> Rubrics;
	

}
*/