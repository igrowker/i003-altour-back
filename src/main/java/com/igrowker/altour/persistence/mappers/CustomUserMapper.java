package com.igrowker.altour.persistence.mappers;

import com.igrowker.altour.dtos.internal.User.UserReadDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import org.springframework.stereotype.Component;

@Component
public class CustomUserMapper {

    public UserReadDTO toUserReadDto(CustomUser user){
        return UserReadDTO.builder()
                .id(user.getId())
                .username(user.getRealUsername())
                .email(user.getEmail())
                .acceptedTOS(user.getAcceptedTOS())
                .maxSearchDistance(user.getMaxSearchDistance())
                .preferredCrowdLevel(user.getPreferredCrowdLevel())
                .preferences(user.getPreferences())
                .favorites(user.getFavorites())
                .build();
    }

}
