package org.example.backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.backend.dto.RegistrationRequestDTO;
import org.example.backend.dto.UserDTO;
import org.example.backend.dto.UserUpdateRequest;
import org.example.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsersDTO();
    }

    //TODO: TournamentController letesztelése
    //TODO: Bet CRUD

    @GetMapping("/fetch/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserDTOByUsername(username));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDTOByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateOwnUser(@PathVariable Long id,@Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userService.updateOwnUser(id, request));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody RegistrationRequestDTO request){
        return ResponseEntity.ok(userService.createNewUser(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("Deletion successful");
    }
}
