package com.igrowker.altour.dtos.internal.User;


import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadDTO {
    private Long id;

    private String username;

    private String email;

    private String password;

    private Boolean acceptedTOS;

    private Integer maxSearchDistance;

    private Integer preferredCrowdLevel; // Nivel de afluencia preferido de 10 a 100

    private Set<VenueType> preferences = new HashSet<>();

 //   private Set<UserFavorite> favorites = new HashSet<>();
    private Set<Place> favorites = new HashSet<>();

    // private Set<UserVisitedDestination> visitedDestinations = new HashSet<>();

    public CustomUser toEntity() {
        CustomUser user = new CustomUser();
        user.setId(this.id);
        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setAcceptedTOS(this.getAcceptedTOS());
        user.setMaxSearchDistance(this.maxSearchDistance);
        user.setPreferredCrowdLevel(this.preferredCrowdLevel);
        user.setPreferences(this.preferences);
        user.setFavorites(this.favorites);
       // user.setVisitedDestinations(this.visitedDestinations);

        return user;
    }
}
