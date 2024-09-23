package com.igrowker.altour.controller;

import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.UserReadDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("users")
@Tag(name = "USUARIOS")
public class UserController {

    @Autowired
    private IUserService userService;


    // todo VERIFICAR FUNCIONAMIENTO
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {        ;
        return new ResponseEntity<>(userService.deleteUser(loginUserDTO.getEmail()), HttpStatus.OK);
    }
    // todo TERMINAR LOGICA
    @PutMapping("/profile/")
    public ResponseEntity<UserReadDTO> updateUser(@RequestBody UserReadDTO userReadDTO,
                                                  Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.updateUser(user.getId(), userReadDTO), HttpStatus.ACCEPTED);
    }


    @GetMapping("/profile/")
    public ResponseEntity<UserReadDTO> getUser(Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.findUserById(user.getId()), HttpStatus.OK);
    }

    // PREFERENCIAS
    @GetMapping("/preferences/")
    public ResponseEntity<Set<VenueType>> getPreferences(Authentication authentication) {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.getPreferencesByEmail(userDetails.getUsername()) , HttpStatus.OK);
    }
    @PostMapping("/preferences/")
    public ResponseEntity<String> addPreference(@RequestParam String preference, Authentication authentication) {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.addPreference(userDetails.getUsername(), preference) , HttpStatus.CREATED);
    }
    @DeleteMapping("/preferences/")
    public ResponseEntity<String> removePreference(@RequestParam String preference, Authentication authentication) {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.removePreference(userDetails.getUsername(), preference), HttpStatus.OK);
    }

    // FAVORITOS
    @GetMapping("/favorites/")
    public ResponseEntity<Set<Place>> getFavorites(Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.getAllFavorites(user.getId()), HttpStatus.OK);
    }
    @PostMapping("/favorites/")
    public ResponseEntity<String> addFavorite(@RequestParam String externalIdPlace,
                                              Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.addFavorite(user.getId(), externalIdPlace), HttpStatus.CREATED);
    }
    @DeleteMapping("/favorites/")
    public ResponseEntity<String> deleteFavorite(@RequestParam String externalIdPlace,
                                                 Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.deleteFavorite(user.getId(), externalIdPlace), HttpStatus.OK);
    }
}
