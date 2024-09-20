package com.igrowker.altour.dtos.internal.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data // getters y setters
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserDTO {

    @NotNull(message = "Email no puede ser nulo")
    @Email(message = "Email con formato invalido")
    private String email;

    @NotNull(message = "Contraseña no puede ser nula")
    @Size(min=2, max=30, message = "Contraseña debe tener entre 2 y 30 caracteres")
    private String password;

}
