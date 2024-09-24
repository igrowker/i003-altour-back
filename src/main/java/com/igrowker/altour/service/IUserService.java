package com.igrowker.altour.service;

import com.igrowker.altour.dtos.internal.User.AuthResponse;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.RegisterUserDT0;
import com.igrowker.altour.dtos.internal.User.UserReadDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;

import java.util.Set;

public interface IUserService {

    UserReadDTO findUserById(Long id);
    CustomUser getUserByEmail(String email);
    CustomUser getUserById(Long id);

    // USER CONFIG PERFIL
    UserReadDTO updateUser(Long userId, UserReadDTO user);
    String deleteUser(LoginUserDTO loginUserDTO);

    // USERS- PLACES
    Set<Place> getAllFavorites(Long userId);
    Set<Place> addFavorite (Long userId,String externalIdPlace);
    Set<Place> deleteFavorite(Long userId, String externalIdPlace);

    // SECURITY
    AuthResponse login (LoginUserDTO loginUserDTO);
    void validateNewEmail(String email);
    AuthResponse register (RegisterUserDT0 user);

    // PREFERENCES
    Set<VenueType> getPreferencesByEmail (String preference);
    Set<VenueType> addPreference (String email, String newPreference);
    Set<VenueType> removePreference (String email, String preferenceToRemove);

}
