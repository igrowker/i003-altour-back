package com.igrowker.altour.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class CustomUser implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false , unique = true)
	private String username; // todo yo lo eliminaria, porque personalmente no me gusta, pero como decidan

	@Column(nullable = false , unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String activity;

	@Column(nullable = false)
	private BigDecimal maxDistance;

	// todo aca agregaria la lista de favoritos, con los id de los destinos favoritos. De la misma manera, se podria crear un lsitado de los visitados y de ahi se obtendria una recomendacion mas personalizada

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}
}
