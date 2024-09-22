package com.igrowker.altour.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class CustomUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String username; // todo yo lo eliminaria, porque personalmente no me gusta, pero como decidan

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	// Distancia máxima de búsqueda
	@Column(name = "max_search_distance", nullable = false)
	private Integer maxSearchDistance;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@Builder.Default
	private Set<UserPreference> preferences = new HashSet<>();

	@Column(name = "preferred_crowd_level", nullable = false)
	private Integer preferredCrowdLevel; // TODO que escala de valores usaremos para esto? Nivel de afluencia preferido


	@Override
	public String getUsername() {
		return email;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getPassword() {
		return password;
	}



// todo metodos a verificar
// todo metodos a verificar
// todo metodos a verificar
// todo metodos a verificar

//todo un usuario tiene muchos favoritos, y un favorito puede estar relacionado a muchos usuarios, sin embargo, el favorito no conoce al usuario, por lo que el id del destino se tiene que meter en una lista de destinos favoritos en el user, como fk.. Aplicaria lo mismo para visitados.. CHARLAR
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@Builder.Default
	private Set<UserFavorite> favorites = new HashSet<>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@Builder.Default
	private Set<UserVisitedDestination> visitedDestinations = new HashSet<>();
// todo metodos a verificar
// todo metodos a verificar
// todo metodos a verificar
// todo metodos a verificar


}
