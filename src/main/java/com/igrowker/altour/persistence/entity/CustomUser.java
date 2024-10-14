package com.igrowker.altour.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;


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
	private String username;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@ColumnDefault(value = "false")
	private Boolean acceptedTOS;

	@Column(name = "max_search_distance", nullable = false)
	private Integer maxSearchDistance;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_venue_types",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "venue_type_id")
	)
	private Set<VenueType> preferences = new HashSet<>();

	@Column(name = "preferred_crowd_level", nullable = false)
	private Integer preferredCrowdLevel; // TODO que escala de valores usaremos para esto? Nivel de afluencia preferido


	@Override
	public String getUsername() {
		return email;
	}
	public String getRealUsername() {
		return username;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}
	@Override
	public String getPassword() {
		return password;
	}

	// TODO VER FORMAS DE TENER SOLO UNA LISTA Y UN ESTADO, TIPO VISITADOS, PENDIENTES FAVORITOS ECTS..
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "user_places",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "place_id")
			/* columnDefinition = "Long INT, place_id INT, status VARCHAR(10)" */)
	private Set<Place> favorites = new HashSet<>();

}
