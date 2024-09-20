package com.igrowker.altour.dtos.internal.User;


import com.igrowker.altour.persistence.entity.CustomUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private String activity; // TODO VERIFICAR SI ENUM CON LISTA DE ACT O STRING

    private BigDecimal maxDistance;

    public CustomUser toEntity() {
        CustomUser user = new CustomUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setActivity(this.activity);
        user.setMaxDistance(this.maxDistance);
        return user;
    }
}
