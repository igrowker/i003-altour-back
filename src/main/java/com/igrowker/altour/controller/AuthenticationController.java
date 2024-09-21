package com.igrowker.altour.controller;

import com.igrowker.altour.dtos.internal.User.RegistserUserDT0;
import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.persistence.entity.CustomUser;
import com.igrowker.altour.service.UserServiceImplementation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public class AuthenticationController {
    @Autowired
    private UserServiceImplementation userServiceImplementation;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        return new ResponseEntity<>(userServiceImplementation.login(loginUserDTO), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegistserUserDT0 user) {
        return new ResponseEntity<>(userServiceImplementation.register(user), HttpStatus.CREATED);
    }

    @GetMapping("/details")
    public ResponseEntity<?> userDetails(@RequestHeader HttpHeaders headers) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        // CustomUser logguedUser = (CustomUser) securityContext.getAuthentication().getPrincipal();
        // System.out.println("LOGUED USER =================== >> > >  "+logguedUser.getEmail());

        return new ResponseEntity<>("OGUED USER", HttpStatus.OK);
    }
}
