package com.igrowker.altour.dtos.internal.User;


import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.UserFavorite;
import com.igrowker.altour.persistence.entity.UserPreference;
import com.igrowker.altour.persistence.entity.UserVisitedDestination;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class UserDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private Integer maxSearchDistance;

    // Nivel de afluencia preferido
    private Integer preferredCrowdLevel;

    private Set<UserPreference> preferences = new HashSet<>();

    private Set<UserFavorite> favorites = new HashSet<>();

    private Set<UserVisitedDestination> visitedDestinations = new HashSet<>();

    public CustomUser toEntity() {
        CustomUser user = new CustomUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setMaxSearchDistance(this.maxSearchDistance);
        user.setPreferredCrowdLevel(this.preferredCrowdLevel);
        user.setPreferences(this.preferences);
        user.setFavorites(this.favorites);
        user.setVisitedDestinations(this.visitedDestinations);

        return user;
    }
}
