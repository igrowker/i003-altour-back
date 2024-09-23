package com.igrowker.altour.dtos.internal.User;

import com.igrowker.altour.persistence.entity.UserFavorite;
import com.igrowker.altour.persistence.entity.UserVisitedDestination;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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


    // TODO LOS OTROS ATRIBUTOS FALTANTES DE USUARIO SE DEBERIAN CARGAR UNA VEZ QUE EXISTA EL USUARIO.. POR LO QUE NO DEBERIA HACERSE CON UN REGISTER USER DTO SINO CON USER DTO

}
