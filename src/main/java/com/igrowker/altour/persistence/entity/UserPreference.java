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
@Table(name = "user_preferences")
public class UserPreference {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// TODO aca esto deberia comentarse y que la preferencia no sepa que usuarios tiene relacionados.. pero implica cambiar la logica de busqueda en repository.. hablarlo con chicos, ya que puede modificarse la logica
	@ManyToOne
	// @JoinColumn(name = "user_id", nullable = false)
	private CustomUser user;

	@Column(nullable = false)
	private String preference;

}