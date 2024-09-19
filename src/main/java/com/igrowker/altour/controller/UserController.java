package com.igrowker.altour.controller;

import com.igrowker.altour.api.externalDtos.User.LoginUserDTO;
import com.igrowker.altour.api.externalDtos.User.UserDTO;
import com.igrowker.altour.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping()
    public ResponseEntity<?> getUser(@RequestBody LoginUserDTO loginUserDTO) {

        if (loginUserDTO == null || loginUserDTO.getEmail() == null || loginUserDTO.getEmail().isEmpty() ||
                loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        if (userService.getUser(loginUserDTO.getEmail()) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.getUser(loginUserDTO.getEmail()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody LoginUserDTO loginUserDTO) {

        if (loginUserDTO == null || loginUserDTO.getEmail() == null || loginUserDTO.getEmail().isEmpty() ||
                loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        if (userService.getUser(loginUserDTO.getEmail()) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(loginUserDTO.getEmail());
        return new ResponseEntity<>("User deleted", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {

        if (userDTO == null || userDTO.getEmail() == null || userDTO.getEmail().isEmpty() ||
                userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        if (userService.getUser(userDTO.getEmail()) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.updateUser(userDTO.toEntity());

        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }
}
