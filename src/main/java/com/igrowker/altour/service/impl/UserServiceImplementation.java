package com.igrowker.altour.service.impl;

import com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.RegisterUserDT0;
import com.igrowker.altour.dtos.internal.User.UserReadDTO;
import com.igrowker.altour.exceptions.ForbiddenException;
import com.igrowker.altour.exceptions.InvalidInputException;
import com.igrowker.altour.exceptions.NotFoundException;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.Place;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.persistence.mappers.CustomUserMapper;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.persistence.repository.IVenueTypeRepository;
import com.igrowker.altour.service.IPlaceService;
import com.igrowker.altour.service.IUserService;
import com.igrowker.altour.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

// PARA SPRING SECURITY
@Service
public class UserServiceImplementation implements IUserService {

    @Autowired
    private ICustomUserRepository userRepository;
    @Autowired
    private PasswordEncoder  passwordEncoder;
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



    // USER CONFIG PERFIL
    // TODO estos metodos deben ser llamados desde el UPDATE DE USER
    public void setMaxDistance(String username, Integer maxDistance) {
        // TODO VERIFICAR
        // TODO VERIFICAR
        // TODO VERIFICAR
        // TODO VERIFICAR
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setMaxSearchDistance(maxDistance);
        userRepository.save(user);
    }
    // TODO estos metodos deben ser llamados desde el UPDATE DE USER
    public void setCrowdLevel(String username, Integer crowdLevel) {
        // TODO VERIFICAR
        // TODO VERIFICAR
        // TODO VERIFICAR
        // TODO VERIFICAR
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPreferredCrowdLevel(crowdLevel);
        userRepository.save(user);
    }
    @Override
    public UserReadDTO updateUser(Long userId, UserReadDTO userUpdate) {
        CustomUser user = getUserById(userId);
        // NO SE PUEDE CAMBIAR username ni email y si, los permisos ya fueron acceptados no se puede retirar la aprobacion.
        if( userUpdate.getUsername() != null && ! userUpdate.getUsername().equals(user.getRealUsername())) throw new ForbiddenException("NO esta permitido cambiar username");
        if( userUpdate.getEmail() != null && ! userUpdate.getEmail().equals(user.getEmail())) throw new ForbiddenException("NO esta permitido cambiar email");
        if(! userUpdate.getAcceptedTOS() && user.getAcceptedTOS() ) throw new ForbiddenException("Ya no puedes retirar los permisos de uso, pero puedes eliminar la cuenta");
        // PREFERENCES y FAVORITES debes ser maenjados a traves de sus respectivos endpoints
        if(! userUpdate.getFavorites().isEmpty() ) throw new ForbiddenException("Los favoritos del usuario solo se pueden modificar a traves de sus endpoints");
        // todo => if(! userUpdate.getVisitedDestinations().isEmpty() ) throw new ForbiddenException("Los Lugares visitados del usuario solo se pueden modificar a traves de sus endpoints");

        // Casos permitidos
        if(! userUpdate.getPassword().isEmpty() && !passwordEncoder.matches(userUpdate.getPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(userUpdate.getPassword()));
        }
        if( userUpdate.getAcceptedTOS() ) user.setAcceptedTOS(true);
        if( userUpdate.getMaxSearchDistance() >0 ) user.setMaxSearchDistance(userUpdate.getMaxSearchDistance());
        if( userUpdate.getPreferredCrowdLevel() >0 && userUpdate.getPreferredCrowdLevel() <=100 ) user.setMaxSearchDistance(userUpdate.getPreferredCrowdLevel());

        userRepository.save(user);
        return userMapper.toUserReadDto(user);
    }
    @Override
    public String deleteUser(String email) {
        userRepository.delete(getUserByEmail(email)); // este metodo lanzara exepcion sin no lo borra, por lo que cortara la ejecucion
        return "Usuario eliminado";
    }






    @Override
    public UserReadDTO findUserById(Long id) { return  userMapper.toUserReadDto(userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found")));}

    @Override
    public CustomUser getUserByEmail(String email) { return userRepository.findByEmail(email).orElseThrow(()-> new NotFoundException("User not found")); }
    @Override
    public CustomUser getUserById(Long id) { return userRepository.findById(id).orElseThrow(()-> new NotFoundException("User not found"));}

    // FAVORITES
    @Override
    public String addFavorite(Long userId, String externalIdPlace) {
        CustomUser user = getUserById(userId);
        Optional<Place> placeToAdd = placeService.findPlaceByExternalAPI(externalIdPlace);

        if (placeToAdd.isEmpty()) {
            // TODO si no existe, VERIFICAR DE QUE API ES EL ID
            if (externalIdPlace.contains("venue")) {
                // TODO LLAMAR A API DE BESTTIME
                // TODO LLAMAR A API DE BESTTIME
                // TODO LLAMAR A API DE BESTTIME
            } else {
                // TODO LLAMAR A API DE HERE MAPS
                // TODO LLAMAR A API DE HERE MAPS
                // TODO LLAMAR A API DE HERE MAPS
            }
            // PERSISITIR LA INFO OBTENIDA DESDE LA API
            // AGREGAR A FAV user.getFavorites().add(placeToAdd.get());
            // GUARDAR USUARIO userRepository.save(user);
        } else {
            user.getFavorites().add(placeToAdd.get());
            userRepository.save(user);
        }
        return "ESTO ESTA BIEN? DEBERIAMOS RESPONDER OTRA COSA ME PARECE Se agrego favorito con nombre: ==>>> ";
    }
    @Override
    public Set<Place> getAllFavorites(Long userId) {
        return getUserById(userId).getFavorites();
    }
    @Override
    public String deleteFavorite(Long userId, String externalIdPlace) {
        CustomUser user = getUserById(userId);
        Optional<Place> placeToRemove = placeService.findPlaceByExternalAPI(externalIdPlace);
        if(placeToRemove.isPresent()){
            user.getFavorites().remove(placeToRemove.get());
            userRepository.save(user);
            return "ESTO ESTA BIEN? DEBERIAMOS RESPONDER OTRA COSA ME PARECE Se elimino favorito con nombre: ==>>> "+placeToRemove.get().getName();
        }
        throw new NotFoundException("No se encontro el favorito a eliminar");
    }

    // PREFERENCES
    @Override
    public Set<VenueType> getPreferencesByEmail(String email) {  return  getUserByEmail(email).getPreferences();  }
    @Override
    public String addPreference(String email, String newPreference) {
        CustomUser user = getUserByEmail(email);
        String venueTypeName = EnumVenueTypes.valueOf(newPreference).name(); // TODO VERIFICAR EXCEPCION QUE PUEDE LANZAR DESDE FRONT SI NO ES UN ENUM VALIDO => IllegalArgumentException "No enum constant com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes.Museo",
        Optional<VenueType> prefToAdd = venueTypeRepository.findByVenueType(venueTypeName);

        if(prefToAdd.isEmpty()){
            VenueType venueTypeToAdd = VenueType.builder().venueType(venueTypeName).build();
            user.getPreferences().add(venueTypeRepository.save(venueTypeToAdd));
        } else {
            user.getPreferences().add(prefToAdd.get());
        }
        userRepository.save(user);
        return "(ES NECESARIO RESPODNER ESTO? DEBERIAMOS RESPONDER CON UN DTO DE USER PARA ACTUALZIAR DATOS DE USUARIO EN FRONT?? )Preferencia agregada: "+newPreference;
    }
    @Override
    public String removePreference(String email, String preferenceToRemove) {
        CustomUser user = getUserByEmail(email);
        String venueTypeName = EnumVenueTypes.valueOf(preferenceToRemove).name();// TODO VERIFICAR EXCEPCION QUE PUEDE LANZAR DESDE FRONT SI NO ES UN ENUM VALIDO => IllegalArgumentException "No enum constant com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes.Museo",
        Optional<VenueType> prefToRemove = venueTypeRepository.findByVenueType(venueTypeName);
        prefToRemove.ifPresent(venueType -> user.getPreferences().remove(venueType));
        userRepository.save(user);
        return "(ES NECESARIO RESPODNER ESTO? DEBERIAMOS RESPONDER CON UN DTO DE USER PARA ACTUALZIAR DATOS DE USUARIO EN FRONT?? )Preferencia eliminada: "+preferenceToRemove;
    }

    // AUTH
    @Override
    public String login(LoginUserDTO loginUserDTO) {
        CustomUser dbUser = getUserByEmail(loginUserDTO.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword()));
        return jwtUtils.generateToken(dbUser);
    }
    @Override
    public String register(RegisterUserDT0 user) {
        validateNewEmail(user.getEmail());
        if (! user.getPassword().equals(user.getConfirmPassword())) throw new InvalidInputException("Passwords no concuerdan!");
        CustomUser newUser = CustomUser.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .acceptedTOS(user.getAcceptedTOS())
                .favorites(new HashSet<>())
                .maxSearchDistance(1000) // todo valor por defecto, front que lo modifique en un update
                .preferences(new HashSet<>())
                .preferredCrowdLevel(80) // todo Nivel de afluencia preferido de 0 a 100 => Avisar a front que TIENE QUE SER MAYOR A 10 y menor a 100
                // .visitedDestinations(new HashSet<>())
                .build();
        userRepository.save(newUser);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        return jwtUtils.generateToken(newUser);

    }
    @Override
    public void validateNewEmail(String email) {
        if (userRepository.existsByEmail(email)) throw new RuntimeException("This Email is already registered!");// todo CAMBIAR MANEJO EXEPCIONES PERSONALIZADAS
    }

}
