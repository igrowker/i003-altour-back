package com.igrowker.altour.controller;

import com.igrowker.altour.dtos.internal.User.RegisterUserDT0;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.service.impl.UserServiceImplementation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
@Tag(name = "Autenticacion - Seguridad")
public class AuthenticationController {

    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        return new ResponseEntity<>(userServiceImplementation.login(loginUserDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterUserDT0 user) {
        return new ResponseEntity<>(userServiceImplementation.register(user), HttpStatus.CREATED);
    }
}
