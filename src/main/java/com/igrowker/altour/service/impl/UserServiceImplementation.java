package com.igrowker.altour.service.impl;

import com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.RegistserUserDT0;
import com.igrowker.altour.dtos.internal.User.UserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.persistence.entity.VenueType;
import com.igrowker.altour.persistence.repository.ICustomUserRepository;
import com.igrowker.altour.persistence.repository.IVenueTypeRepository;
import com.igrowker.altour.service.IUserService;
import com.igrowker.altour.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public void validateNewEmail(String email) {
        if (userRepository.existsByEmail(email)) throw new RuntimeException("This Email is already registered!");// todo CAMBIAR MANEJO EXEPCIONES PERSONALIZADAS
    }

    @Override
    public CustomUser getUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found")); // todo CAMBIAR MANEJO EXEPCIONES PERSONALIZADAS
    }

    @Override
    public String login(LoginUserDTO loginUserDTO) {
        CustomUser dbUser = getUser(loginUserDTO.getEmail());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmail(), loginUserDTO.getPassword()));
        // todo: authenticationManager.authenticate(UsernamePasswordAuthenticationToken.class) es el metodo que se encarga de pedir un objeto con las credenciales y pasarlo al authentication provider correcto, El authentication provider (seteado en el archivo SecurityConfig mediante el metodo authenticationProvider()), se encarga de llamar al CustomUserDetailsServiceImpl para que este busque los detalles del user con metodo loadUserByUsername() busca al user en la bd o desde otra api de terceros. Permitiendo configurar multiples formas de authenticacion. Y si fue correcto se crea un objeto Authentication (con los datos del user) que se guarda en el SecurityContextHolder. Sino se lanza excepción BadCredentials y no retornara token
        return jwtUtils.generateToken(dbUser);
    }

    @Override
    public String register(RegistserUserDT0 user) {
        validateNewEmail(user.getEmail());
        CustomUser newUser = CustomUser.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(passwordEncoder.encode(user.getPassword()))
                .favorites(new HashSet<>())
                .maxSearchDistance(1000) // todo valor por defecto, front que lo modifique en un update
                .preferences(new HashSet<>())
                .preferredCrowdLevel(80) // todo Nivel de afluencia preferido de 0 a 100 => Avisar a front que TIENE QUE SER MAYOR A 10 y menor a 100
                .visitedDestinations(new HashSet<>())
                .build();
        userRepository.save(newUser);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        // todo: authenticationManager.authenticate(UsernamePasswordAuthenticationToken.class) es el metodo que se encarga de pedir un objeto con las credenciales y pasarlo al authentication provider correcto, El authentication provider (seteado en el archivo SecurityConfig mediante el metodo authenticationProvider()), se encarga de llamar al CustomUserDetailsServiceImpl para que este busque los detalles del user con metodo loadUserByUsername() busca al user en la bd o desde otra api de terceros. Permitiendo configurar multiples formas de authenticacion. Y si fue correcto se crea un objeto Authentication (con los datos del user) que se guarda en el SecurityContextHolder. Sino se lanza excepción BadCredentials y no retornara token
        return jwtUtils.generateToken(newUser);

    }

    @Override
    public boolean checkCredentials(String email, String password) {
        Optional<CustomUser> user = userRepository.findByEmail(email);
        if (user.isEmpty()) return false;
        return passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    public CustomUser updateUser(UserDTO user) {
        // todo Determinar con front, si para actualziar hay que pasar todos los campos, sino se deberia chequear y actualziar uno por uno..
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new RuntimeException("Missing params"); // todo CAMBIAR MANEJO EXEPCIONES PERSONALIZADAS
        }
        if(userRepository.existsByEmail(user.getEmail())) throw new RuntimeException("User not found"); // todo CAMBIAR MANEJO EXEPCIONES PERSONALIZADAS
        return userRepository.save(user.toEntity());
    }

    @Override
    public String deleteUser(String email) {
        userRepository.delete(getUser(email)); // este metodo lanzara exepcion sin no lo borra, por lo que cortara la ejecucion
        return "Usuario eliminado";
    }


    public void setMaxDistance(String username, Integer maxDistance) {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setMaxSearchDistance(maxDistance);
        userRepository.save(user);
    }

    public void setCrowdLevel(String username, Integer crowdLevel) {
        CustomUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPreferredCrowdLevel(crowdLevel);
        userRepository.save(user);
    }

    @Override
    public Set<VenueType> getPreferencesByEmail(String email) {  return  getUser(email).getPreferences();  }

    @Override
    public String addPreference(String email, String newPreference) {
        CustomUser user = getUser(email);
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
        CustomUser user = getUser(email);
        String venueTypeName = EnumVenueTypes.valueOf(preferenceToRemove).name();// TODO VERIFICAR EXCEPCION QUE PUEDE LANZAR DESDE FRONT SI NO ES UN ENUM VALIDO => IllegalArgumentException "No enum constant com.igrowker.altour.dtos.external.bestTimeApi.EnumVenueTypes.Museo",
        Optional<VenueType> prefToRemove = venueTypeRepository.findByVenueType(venueTypeName);
        prefToRemove.ifPresent(venueType -> user.getPreferences().remove(venueType));
        userRepository.save(user);
        return "(ES NECESARIO RESPODNER ESTO? DEBERIAMOS RESPONDER CON UN DTO DE USER PARA ACTUALZIAR DATOS DE USUARIO EN FRONT?? )Preferencia eliminada: "+preferenceToRemove;
    }
}
