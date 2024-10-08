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
import org.springframework.beans.factory.annotation.Value;
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
    
	@Value("${best_time.api.pub.key}")
	private String bestTimeApiPubKey;

    @DeleteMapping
    public ResponseEntity<String> deleteUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        return new ResponseEntity<>(userService.deleteUser(loginUserDTO), HttpStatus.OK);
    }

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
    public ResponseEntity<Set<VenueType>> addPreference(@RequestParam String preference, Authentication authentication) {
        CustomUser userDetails = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.addPreference(userDetails.getUsername(), preference) , HttpStatus.CREATED);
    }
    @DeleteMapping("/preferences/")
    public ResponseEntity<Set<VenueType>> removePreference(@RequestParam String preference, Authentication authentication) {
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
    public ResponseEntity<Set<Place>> addFavorite(@RequestParam String externalIdPlace,
                                              Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.addFavorite(user.getId(), externalIdPlace, bestTimeApiPubKey), HttpStatus.CREATED);
    }
    @DeleteMapping("/favorites/")
    public ResponseEntity<Set<Place>> deleteFavorite(@RequestParam String externalIdPlace,
                                                 Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>(userService.deleteFavorite(user.getId(), externalIdPlace), HttpStatus.OK);
    }
}
