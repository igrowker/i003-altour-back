package com.igrowker.altour.controller;

import com.igrowker.altour.api.externalDtos.User.UserDTO;
import com.igrowker.altour.api.externalDtos.User.LoginUserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.service.UserServiceImplementation;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.util.Date;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final int EXPIRATION_TIME = 60 * 1000 * 120; //* 120 minutos

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDTO loginUserDTO) {

        if (loginUserDTO == null || loginUserDTO.getEmail() == null || loginUserDTO.getEmail().isEmpty() ||
                loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }


       // todo esta logica la pasaria al service, incluyendo buscar al user, responder con not found en caso de que no, el validar credenciales y el crear el jwt, solo dejaria en el controler lo que es la respuesta en si..
        CustomUser dbUser = userServiceImplementation.getUser(loginUserDTO.getEmail());
        if (dbUser == null) {
            // todo usualmente esta logica de retornar error de notFound yo la suelo delegar al service directamente, porque asi evitamos tener que hacer el if en cada controller que pueda no encontrar al user
            // todo lei por ahi que suele ser buena practica responder con un badCredentials y no especificar si es por el username o el password, como para evitar dar info de que ese usuario existe o no.. tema de gustos..
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!userServiceImplementation.checkCredentials(loginUserDTO.getEmail(), loginUserDTO.getPassword())) {
            // todo lei por ahi que suele ser buena practica responder con un badCredentials y no especificar si es por el username o el password, como para evitar dar info de que ese usuario existe o no.. tema de gustos..
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        String token = Jwts.builder()
                .setSubject(loginUserDTO.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        // todo aqui suelo crear una clase que contiente la respuesta del token, es lo mismo, solo costumbre.. tipo AuthDTO { String token }
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO user) {

        // todo toda la logica de este metodo la pasaria al service, me parece mas responsable de validar al usuario y guardarlo.. como que el controller me parece que deberia solo responder
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            // todo estas validaciones las podriamos obviar si usamos la etiqueta @Valid en el controller y en el UserDTO determinamos las cosas obligatorias que debe traer el register ej @Email, o @NotEmpty, min max, etcs, podriamos crear un dto especifico de registro que valide estos campos y asi no nos complica si queremos manejar distinta la logica de los updateUser
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        CustomUser dbUser = userServiceImplementation.getUser(user.getEmail());
        if (dbUser != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }


        // todo comento esta linea porque toda la logica implementada en el controler la pasaria a el userServiceImplementation, ya que personalmente lo veo mas limpio..

        // todo por otro lado, aqui haria un mapper, tipo userDtoToUserEntity() para separar la responsabilidad en otra clase
        CustomUser newUser = new CustomUser();
        newUser.setEmail(user.getEmail());
        // newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setPassword(user.getPassword());
        newUser.setActivity(user.getActivity());
        newUser.setMaxDistance(user.getMaxDistance());
        newUser.setUsername(user.getUsername());

        userServiceImplementation.saveUser(newUser);

        // todo aqui suelo retornar el jwt, como cuando iniciamos sesion para hacer dos pasos en uno y evitar loguar la proxima vez
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }
}
