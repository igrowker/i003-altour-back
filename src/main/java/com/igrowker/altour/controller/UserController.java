package com.igrowker.altour.controller;

import com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.UserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.UserFavorite;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.service.IUserFavoriteService;
import com.igrowker.altour.service.IUserService;
import com.igrowker.altour.service.impl.UserServiceImplementation;
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
    @Autowired
    private IUserFavoriteService favoriteService;
    /*
    @Autowired
    private IUserPreferenceService userPreferenceService;

     */

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {        ;
        return new ResponseEntity<>(userService.deleteUser(loginUserDTO.getEmail()), HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getUser(Authentication authentication) {
        CustomUser user = (CustomUser) authentication.getPrincipal();
        return new ResponseEntity<>( UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .maxSearchDistance(user.getMaxSearchDistance())
                .username(user.getUsername())
                .preferredCrowdLevel(user.getPreferredCrowdLevel())
                .preferences(new HashSet<>()) // todo ERROR => Could not write JSON: failed to lazily initialize a collection of role: com.igrowker.altour.persistence.entity.CustomUser.preferences: could not initialize proxy - no Session
                .favorites(new HashSet<>()) // todo ERROR => Could not write JSON: failed to lazily initialize a collection of role: com.igrowker.altour.persistence.entity.CustomUser.fav: could not initialize proxy - no Session
                .visitedDestinations(new HashSet<>())// todo ERROR => Could not write JSON: failed to lazily initialize a collection of role: com.igrowker.altour.persistence.entity.CustomUser.fav: could not initialize proxy - no Session
                .build(),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        // todo aca deberiamos modificar los campos crowdLevel y maxDistance
        // todo aca deberiamos modificar los campos crowdLevel y maxDistance
        // todo aca deberiamos modificar los campos crowdLevel y maxDistance
        // todo aca deberiamos modificar los campos crowdLevel y maxDistance
        // todo aca deberiamos modificar los campos crowdLevel y maxDistance

        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.ACCEPTED);
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<UserFavorite>> getFavorites(@RequestBody String username) {
        try {
            List<UserFavorite> favorites = favoriteService.getFavoritesByUsername(username);
            return ResponseEntity.ok(favorites);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }
    @PostMapping("/favorites")
    public ResponseEntity<String> addFavorite(@RequestParam String username, @RequestParam Long venueId) {
        try {
            favoriteService.addFavorite(username, venueId);
            return ResponseEntity.ok("Favorite added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding favorite");
        }
    }
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
}
