package com.igrowker.altour.persistence.entity;

import lombok.Data;

@Data
public class RegisterCustomUserDTO {
	private String username;
	private String password;
	private String email;

	// Estoy pasando unos valores predeterminados en el controller que luego el
	// usuario podra modificar para ajustar sus preferencias
	private Integer maxSearchDistance;
	private Integer preferredCrowdLevel;
}
