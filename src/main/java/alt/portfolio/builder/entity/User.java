package alt.portfolio.builder.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User implements UserDetails {

	@Id
	private UUID id = UUID.randomUUID();

	@Column(length = 45, nullable = false)
	private String firstname = "";

	@Column(length = 45, nullable = false)
	private String lastname = "";

	@Column(length = 45, nullable = false, unique = true)
	private String username = "";

	@Column(length = 150, nullable = false, unique = true)
	private String email = "";

	@Column(length = 255, nullable = true)
	private String password;

	// ✅ AJOUT : rôle simple stocké en base
	// Valeurs attendues : "USER" ou "ADMIN"
	@Column(length = 10, nullable = false)
	private String role = "USER";

	@OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Profile> profiles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// ✅ Spring attend "ROLE_XXXX"
		return List.of(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	// ✅ Ajouts conseillés pour éviter des bugs Spring Security
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
