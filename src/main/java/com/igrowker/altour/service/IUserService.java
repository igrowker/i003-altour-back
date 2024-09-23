package com.igrowker.altour.service;

import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.RegistserUserDT0;
import com.igrowker.altour.dtos.internal.User.UserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;

import java.util.Set;

public interface IUserService {

    CustomUser getUserByEmail(String email);
    CustomUser getUserById(Long id);

    // USER CONFIG PERFIL
    CustomUser updateUser(UserDTO user);
    String deleteUser(String email);

    // USERS- PLACES
    Set<Place> getAllFavorites(Long userId);
    String addFavorite (Long userId,String externalIdPlace);
    String deleteFavorite(Long userId, String externalIdPlace);

    // SECURITY
    String login (LoginUserDTO loginUserDTO);
    void validateNewEmail(String email);
    String register (RegistserUserDT0 user);

    // PREFERENCES
    Set<VenueType> getPreferencesByEmail (String preference);
    String addPreference (String email, String newPreference);
    String removePreference (String email, String preferenceToRemove);

}
