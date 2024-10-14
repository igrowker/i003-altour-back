package com.igrowker.altour.service.impl;

import com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes;
import com.igrowker.altour.dtos.external.bestTimeApiId.VenueResponse;
import com.igrowker.altour.dtos.internal.User.AuthResponse;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.RegisterUserDT0;
import com.igrowker.altour.dtos.internal.User.UserReadDTO;
import com.igrowker.altour.exceptions.*;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.persistence.mappers.CustomUserMapper;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.persistence.repository.IVenueTypeRepository;
import com.igrowker.altour.service.IDestineBestTimeService;
import com.igrowker.altour.service.IPlaceService;
import com.igrowker.altour.service.IUserService;
import com.igrowker.altour.utils.AESUtils;
import com.igrowker.altour.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

// PARA SPRING SECURITY
@Service
public class UserServiceImplementation implements IUserService {

	@Autowired
	private ICustomUserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	private JWTUtils jwtUtils;
	@Autowired
	private IVenueTypeRepository venueTypeRepository;
	@Autowired
	private IPlaceService placeService;
	@Autowired
	private CustomUserMapper userMapper;
	@Autowired
	private AESUtils aesUtils;
	@Autowired
	private IDestineBestTimeService destineBestTimeService;

	// USER CONFIG PERFIL
	@Override
	@Transactional
	public UserReadDTO updateUser(Long userId, UserReadDTO userUpdate) {
		CustomUser user = getUserById(userId);
		// NO SE PUEDE CAMBIAR username ni email y si, los permisos ya fueron acceptados
		// no se puede retirar la aprobacion.
		if (userUpdate.getUsername() != null && !userUpdate.getUsername().equals(user.getRealUsername()))
			throw new ForbiddenException("NO esta permitido cambiar username");
		if (userUpdate.getEmail() != null && !userUpdate.getEmail().equals(user.getEmail()))
			throw new ForbiddenException("NO esta permitido cambiar email");

		if (userUpdate.getAcceptedTOS() != null && !userUpdate.getAcceptedTOS() && user.getAcceptedTOS()){
				throw new ForbiddenException("Ya no puedes retirar los permisos de uso, pero puedes eliminar la cuenta");
		}

		// PREFERENCES y FAVORITES debes ser maenjados a traves de sus respectivos
		// endpoints
		if (!userUpdate.getFavorites().isEmpty())
			throw new ForbiddenException(
					"Los favoritos del usuario solo se pueden modificar a traves de sus endpoints");

		// Casos permitidos
		if (userUpdate.getPassword() != null && !userUpdate.getPassword().isEmpty()
				&& !passwordEncoder.matches(userUpdate.getPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
		}
		if (userUpdate.getAcceptedTOS() != null && userUpdate.getAcceptedTOS())
			user.setAcceptedTOS(true);
		if (userUpdate.getMaxSearchDistance() != null && userUpdate.getMaxSearchDistance() > 0)
			user.setMaxSearchDistance(userUpdate.getMaxSearchDistance());
		if (userUpdate.getPreferredCrowdLevel() != null && userUpdate.getPreferredCrowdLevel() > 0 && userUpdate.getPreferredCrowdLevel() <= 100)
			user.setPreferredCrowdLevel(userUpdate.getPreferredCrowdLevel());

		userRepository.save(user);
		return userMapper.toUserReadDto(user);
	}

	@Override
	@Transactional
	public String deleteUser(LoginUserDTO loginUserDTO) {
		CustomUser user = getUserByEmail(loginUserDTO.getEmail());
		if (!passwordEncoder.matches(loginUserDTO.getPassword(), user.getPassword()))
			throw new BadCredentialsException();
		userRepository.delete(user);
		return "Usuario eliminado";
	}

	@Override
	@Transactional(readOnly = true)
	public UserReadDTO findUserById(Long id) {
		return userMapper
				.toUserReadDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found")));
	}

	@Override
	@Transactional(readOnly = true)
	public CustomUser getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	@Transactional(readOnly = true)
	public CustomUser getUserById(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
	}

	// FAVORITES
	@Override
	@Transactional
	public Set<Place> addFavorite(Long userId, String externalIdPlace, String apiKey) {
		CustomUser user = getUserById(userId);

		String encryptedExternalID = aesUtils.encrypt(externalIdPlace);

		Optional<Place> placeToAdd = placeService.findPlaceByExternalID(encryptedExternalID);

		if (placeToAdd.isEmpty()) {

			VenueResponse venueResponse = destineBestTimeService.getVenueById(externalIdPlace, apiKey);

			Place newPlace = new Place();
			newPlace.setExternalAPI("BestTime");

			String encryptedName = aesUtils.encrypt(venueResponse.getVenueInfo().getVenueName());

			newPlace.setExternalID(encryptedExternalID);
			newPlace.setName(encryptedName);

			placeService.save(newPlace);
			user.getFavorites().add(newPlace);
		} else {
			user.getFavorites().add(placeToAdd.get());
		}

		userRepository.save(user);
		return getUserById(userId).getFavorites();
	}

	@Override
	@Transactional(readOnly = true)
	public Set<Place> getAllFavorites(Long userId) {
		Set<Place> favorites = getUserById(userId).getFavorites();

		for (Place place : favorites) {
			String decryptedName = aesUtils.decrypt(place.getName());
			String decryptedExternalID = aesUtils.decrypt(place.getExternalID());
			place.setName(decryptedName);
			place.setExternalID(decryptedExternalID);
		}

		return favorites;
	}

	@Override
	@Transactional
	public Set<Place> deleteFavorite(Long userId, String externalIdPlace) {
		CustomUser user = getUserById(userId);

		String encryptedExternalID = aesUtils.encrypt(externalIdPlace);

		Optional<Place> placeToRemove = placeService.findPlaceByExternalID(encryptedExternalID);
		if (placeToRemove.isPresent()) {
			user.getFavorites().remove(placeToRemove.get());
			userRepository.save(user);
			return getUserById(userId).getFavorites();
		}
		throw new NotFoundException("No se encontró el favorito a eliminar");
	}

	// PREFERENCES
	@Override
	@Transactional(readOnly = true)
	public Set<VenueType> getPreferencesByEmail(String email) {
		CustomUser user = getUserByEmail(email);
		Set<VenueType> decryptedPreferences = new HashSet<>();

		for (VenueType venueType : user.getPreferences()) {
			try {

				String decryptedVenueType = aesUtils.decrypt(venueType.getVenueType());

				VenueType decryptedType = VenueType.builder().id(venueType.getId()).venueType(decryptedVenueType)
						.build();
				decryptedPreferences.add(decryptedType);
			} catch (Exception e) {
				throw new RuntimeException("Error al desencriptar la preferencia", e);
			}
		}

		return decryptedPreferences;
	}

	@Override
	@Transactional
	public Set<VenueType> addPreference(String email, String newPreference) {
		CustomUser user = getUserByEmail(email);
		try {
			String venueTypeName = EnumVenueTypes.valueOf(newPreference).name();

			String encryptedVenueType = aesUtils.encrypt(venueTypeName);

			Optional<VenueType> prefToAdd = venueTypeRepository.findByVenueType(encryptedVenueType);
			if (prefToAdd.isEmpty()) {
				VenueType venueTypeToAdd = VenueType.builder().venueType(encryptedVenueType).build();
				user.getPreferences().add(venueTypeRepository.save(venueTypeToAdd));
			} else {
				user.getPreferences().add(prefToAdd.get());
			}
			userRepository.save(user);
			return user.getPreferences();
		} catch (IllegalArgumentException e) {
			throw new InvalidInputException("Preferencia no compatible");
		}

	}

	@Override
	@Transactional
	public Set<VenueType> removePreference(String email, String preferenceToRemove) {
		CustomUser user = getUserByEmail(email);
		try {
			String venueTypeName = EnumVenueTypes.valueOf(preferenceToRemove).name();

			for (VenueType venueType : user.getPreferences()) {
				String decryptedVenueType = aesUtils.decrypt(venueType.getVenueType());
				if (decryptedVenueType.equals(venueTypeName)) {
					user.getPreferences().remove(venueType);
					break;
				}
			}
			userRepository.save(user);
			return user.getPreferences();
		} catch (IllegalArgumentException e) {
			throw new InvalidInputException("Preferencia no compatible");
		} catch (Exception e) {
			throw new RuntimeException("Error al desencriptar las preferencias", e);
		}
	}

	// AUTH
	@Override
	@Transactional
	public AuthResponse login(LoginUserDTO loginUserDTO) {
		CustomUser dbUser = getUserByEmail(loginUserDTO.getEmail());
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword()));
		return new AuthResponse(jwtUtils.generateToken(dbUser));
	}

	@Override
	@Transactional
	public AuthResponse register(RegisterUserDT0 user) {
		validateNewEmail(user.getEmail());
		validateNewUsername(user.getUsername());
		if (!user.getPassword().equals(user.getConfirmPassword()))
			throw new InvalidInputException("Passwords no concuerdan!");
		CustomUser newUser = CustomUser.builder().username(user.getUsername()).email(user.getEmail())
				.password(passwordEncoder.encode(user.getPassword())).acceptedTOS(user.getAcceptedTOS())
				.favorites(new HashSet<>()).maxSearchDistance(1000)
				.preferences(new HashSet<>()).preferredCrowdLevel(80)
				.build();
		userRepository.save(newUser);
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		return new AuthResponse(jwtUtils.generateToken(newUser));
	}

	@Override
	@Transactional(readOnly = true)
	public void validateNewEmail(String email) {
		if (userRepository.existsByEmail(email)) throw new ConflictException("This Email is already registered!");
	}
	@Override
	public void validateNewUsername(String username) {
		if (userRepository.existsByUsername(username)) throw new ConflictException("This Username is already registered!");
	}

}
