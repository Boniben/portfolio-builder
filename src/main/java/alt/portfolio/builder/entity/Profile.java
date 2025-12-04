package alt.portfolio.builder.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter @Setter
public class Profile {
	@Id
	private UUID id= UUID.randomUUID();
	
	@Column(length = 150, nullable = false)
	private String name;
	
	@Column(length=101400, nullable = false)
	private String description;

	@ManyToOne(optional = false)
	private User owner;
	
	
	/*
	//A revoir
	@OneToMany(mappedBy = "id_profile")
	private List<Rubric> Rubrics;
	
	*/
	
}
