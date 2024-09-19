package com.igrowker.altour.controller;

import com.igrowker.altour.api.externalDtos.User.LoginUserDTO;
import com.igrowker.altour.api.externalDtos.User.UserDTO;
import com.igrowker.altour.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping()
    public ResponseEntity<?> getUser(@RequestBody LoginUserDTO loginUserDTO) {

        if (loginUserDTO == null || loginUserDTO.getEmail() == null || loginUserDTO.getEmail().isEmpty() ||
                loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isEmpty()) {
            // todo estas validaciones las podriamos obviar si usamos la etiqueta @Valid en el controller y en el LoginUserDTO determinamos las cosas obligatorias que debe traer el login ej @Email, o @NotEmpty, min max, etcs
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        // todo usualmente esta logica de retornar error de notFound yo la suelo delegar al service directamente, porque asi evitamos tener que hacer el if en cada controller que pueda no encontrar al user
        if (userService.getUser(loginUserDTO.getEmail()) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(userService.getUser(loginUserDTO.getEmail()), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        // todo usualmente esta logica de retornar error de notFound yo la suelo delegar al service directamente, porque asi evitamos tener que hacer el if en cada controller que pueda no encontrar al user
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
            // todo estas validaciones las podriamos obviar si usamos la etiqueta @Valid en el controller y en el UserDTO determinamos las cosas obligatorias que debe traer, pero va a depender de como elegimos actualizar, osea, hay que pasar todos los datos para actualizar? o con que pasen un solo campo lo actualizamos? dependendiendo de eso podemos o no usar @Valid
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        // todo usualmente esta logica de retornar error de notFound yo la suelo delegar al service directamente, porque asi evitamos tener que hacer el if en cada controller que pueda no encontrar al user
        if (userService.getUser(userDTO.getEmail()) == null) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        userService.updateUser(userDTO.toEntity());

        return new ResponseEntity<>("User updated", HttpStatus.OK);
    }
}
