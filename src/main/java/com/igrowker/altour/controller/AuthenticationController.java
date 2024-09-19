package com.igrowker.altour.controller;

import com.igrowker.altour.api.externalDtos.User.registerUserDTO;
import com.igrowker.altour.api.externalDtos.User.loginUserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.service.UserServiceImplementation;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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
@RequestMapping("/v1/api/auth")
public class AuthenticationController {

    private final int EXPIRATION_TIME = 60 * 1000 * 120; //* 120 minutos

    @Autowired
    private SecretKey secretKey;

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginUserDTO loginUserDTO) {

        if (loginUserDTO == null || loginUserDTO.getEmail() == null || loginUserDTO.getEmail().isEmpty() ||
                loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        CustomUser dbUser = userServiceImplementation.getUser(loginUserDTO.getEmail());
        if (dbUser == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        if (!userServiceImplementation.checkCredentials(loginUserDTO.getEmail(), loginUserDTO.getPassword())) {
            return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        String token = Jwts.builder()
                .setSubject(loginUserDTO.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registerUserDTO user) {

        if (user == null || user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        CustomUser dbUser = userServiceImplementation.getUser(user.getEmail());
        if (dbUser != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }

        CustomUser newUser = new CustomUser();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setActivity(user.getActivity());
        newUser.setMaxDistance(user.getMaxDistance());
        newUser.setUsername(user.getUsername());
        
        userServiceImplementation.saveUser(newUser);

        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }
}
