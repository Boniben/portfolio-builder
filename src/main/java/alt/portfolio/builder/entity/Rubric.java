package alt.portfolio.builder.entity;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.CascadeType;
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

	// Items under this rubric
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rubric", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, orphanRemoval = true)
	private List<Item> items;

	public void addCategory(Category category) {
		this.category = category;
	}

}