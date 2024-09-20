package com.igrowker.altour.dtos.internal.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistserUserDT0 {
    @NotNull(message = "Username no puede ser nulo")
    @Size(min=2, max=30, message = "Username debe tener entre 2 y 30 caracteres")
    private String username;

    @NotNull(message = "Email no puede ser nulo")
    @Email(message = "Email con formato invalido")
    private String email;

    @NotNull(message = "Contraseña no puede ser nula")
    @Size(min=2, max=30, message = "Contraseña debe tener entre 2 y 30 caracteres")
    private String password;

    @NotNull(message = "Actividad no puede ser nula")
    @Size(min=2, max=30, message = "Actividad debe tener entre 2 y 30 caracteres")
    private String activity; // TODO VERIFICAR SI ENUM CON LISTA DE ACT O STRING

    @NotNull(message = "Distancia no puede ser nula")
    @Positive(message = "Distancia debe ser mayor a cero")
    private BigDecimal maxDistance;
}
