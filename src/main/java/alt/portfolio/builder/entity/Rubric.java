package alt.portfolio.builder.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rubric {
	@Id
	private UUID id = UUID.randomUUID();

	@Column(length = 120, nullable = false)
	private String name;

	@Column(length = 3, nullable = false)
	private Integer order_;

	// A revoir
	@ManyToOne()
	private Profile profile;

	// ok
	@ManyToOne()
	private Category category;

	public void addCategory(Category category) {
		this.category = category;
	}

}
