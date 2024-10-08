package com.igrowker.altour.persistence.mappers;

import com.igrowker.altour.dtos.internal.User.UserReadDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.utils.AESUtils;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomUserMapper {

	@Autowired
    private AESUtils aesUtils;
	
    public UserReadDTO toUserReadDto(CustomUser user){
    	
   	 Set<VenueType> decryptedPreferences = user.getPreferences().stream().map(venueType -> {
         VenueType decryptedVenueType = new VenueType();
         decryptedVenueType.setId(venueType.getId()); 
         decryptedVenueType.setVenueType(aesUtils.decrypt(venueType.getVenueType())); 
         return decryptedVenueType;
     }).collect(Collectors.toSet());
   	 
   	 Set<Place> decryptedFavorites = user.getFavorites().stream().map(favorite -> {
         Place decryptedFavorite = new Place();
         decryptedFavorite.setId(favorite.getId());
         decryptedFavorite.setExternalAPI(favorite.getExternalAPI());  
         decryptedFavorite.setName(aesUtils.decrypt(favorite.getName()));  
         decryptedFavorite.setExternalID(aesUtils.decrypt(favorite.getExternalID())); 
         return decryptedFavorite;
     }).collect(Collectors.toSet());
    	
        return UserReadDTO.builder()
                .id(user.getId())
                .username(user.getRealUsername())
                .email(user.getEmail())
                .acceptedTOS(user.getAcceptedTOS())
                .maxSearchDistance(user.getMaxSearchDistance())
                .preferredCrowdLevel(user.getPreferredCrowdLevel())
                .preferences(decryptedPreferences)
                .favorites(decryptedFavorites)
                .build();
    }

}
