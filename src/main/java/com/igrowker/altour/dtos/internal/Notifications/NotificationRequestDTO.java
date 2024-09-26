package com.igrowker.altour.dtos.internal.Notifications;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequestDTO {
    @NotNull
    @Email(message = "Email con formato invalido")
    private String email;

    @NotNull
    private long lat;
    @NotNull
    private long lng;
    @NotNull
    private long population;
}
