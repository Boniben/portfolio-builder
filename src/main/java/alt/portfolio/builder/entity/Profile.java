package alt.portfolio.builder.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
	@OrderBy("order_ DESC")
	private List<Rubric> rubrics;
}
