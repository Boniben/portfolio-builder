package alt.portfolio.builder.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Profile {

	@Id
	private UUID id = UUID.randomUUID();

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false, length = 10000)
	private String description;

	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@ManyToOne(optional = false)
	private User owner;

	// Template choisi pour l'export PDF — optionnel (null = template par défaut)
	@ManyToOne(optional = true)
	private Template template;

	// cascade ALL + orphanRemoval : si le profil est supprimé, toutes ses rubriques le sont aussi
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("order_ ASC")
	private List<Rubric> rubrics;
}
