/*package alt.portfolio.builder.entity;


import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Rubric {
	@Id
	private UUID id= UUID.randomUUID();
	
	@Column(length = 120, nullable = false)
	private String name;
	
	@Column(length = 3, nullable = false)
	private Integer order_;
	
	//A revoir
	@ManyToOne()
	private Rubric id_Profile;
	
	
	//a revoir
	@ManyToOne()
	private Category category;
	
	
	
}*/
