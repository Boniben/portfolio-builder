package alt.portfolio.builder.entity;

import java.util.UUID;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
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

	// Indique si la rubrique est visible dans le CV exporté
	// Par défaut : visible (true)
	@Column(nullable = false)
	private boolean visible = true;

	// A revoir
	@ManyToOne()
	private Profile profile;

	// ok
	@ManyToOne()
	private Category category;

	// cascade ALL + orphanRemoval : si la rubrique est supprimée, tous ses éléments le sont aussi
	// @OrderBy : les éléments sont triés par sortOrder croissant
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "rubric", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("sortOrder ASC")
	private List<Item> items;

	public void addCategory(Category category) {
		this.category = category;
	}

}