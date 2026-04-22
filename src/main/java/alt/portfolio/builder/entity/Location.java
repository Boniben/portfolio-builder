package alt.portfolio.builder.entity;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Location {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(length = 120, nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String address;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "location")
    private List<Item> items;

}
