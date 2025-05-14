package org.example.backend.controller;

import jakarta.validation.Valid;
import org.example.backend.dto.RegistrationRequestDTO;
import org.example.backend.dto.UserDTO;
import org.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody RegistrationRequestDTO request){
        UserDTO userDTO = userService.registerUser(request);
        return ResponseEntity.ok(userDTO);
    }
}
