package com.igrowker.altour.controller;

import com.igrowker.altour.dtos.internal.User.LoginUserDTO;
import com.igrowker.altour.dtos.internal.User.UserDTO;
import com.igrowker.altour.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "users")
public class UserController {

    @Autowired
    private IUserService userService;

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {        ;
        return new ResponseEntity<>(userService.deleteUser(loginUserDTO.getEmail()), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<?> getUser(@RequestBody @Valid LoginUserDTO loginUserDTO) {
        // TODO VERIFICAR LA UTILIDAD DE ESTE ENDPOINT, PORQUE SI RECIBIMOS UN JWT PODEMOS OBTENER EL USER DESDE AHI.. POR QUE OTRA RAZON USARIAMOS LAS CREDENCIALES PARA OBTENER UN USUARIO?

        // todo estas validaciones las podriamos obviar si usamos la etiqueta @Valid en el controller y en el LoginUserDTO determinamos las cosas obligatorias que debe traer el login ej @Email, o @NotEmpty, min max, etcs
        if (loginUserDTO == null || loginUserDTO.getEmail() == null || loginUserDTO.getEmail().isEmpty() ||
                loginUserDTO.getPassword() == null || loginUserDTO.getPassword().isEmpty()) {
            return new ResponseEntity<>("Missing params", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userService.getUser(loginUserDTO.getEmail()), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUser(userDTO), HttpStatus.ACCEPTED);
    }
}
