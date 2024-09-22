package com.igrowker.altour.persistence.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_favorites")
public class UserFavorite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// todo un usuario tiene muchos favoritos, y un favorito puede estar relacionado a muchos usuarios, sin embargo, el favorito no conoce al usuario, por lo que el id del destino se tiene que meter en una lista de destinos favoritos en el user, como fk
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private CustomUser user;

	@Column(name = "destination_id", nullable = false)
	private Long destinationId;

}