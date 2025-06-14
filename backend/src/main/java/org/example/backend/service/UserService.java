package org.example.backend.service;

import jakarta.transaction.Transactional;
import org.example.backend.configuration.auth.JwtAuthenticationFilter;
import org.example.backend.configuration.auth.Session;
import org.example.backend.dto.RegistrationRequestDTO;
import org.example.backend.dto.UserDTO;
import org.example.backend.dto.UserUpdateRequest;
import org.example.backend.mapper.UserMapper;
import org.example.backend.model.user.Role;
import org.example.backend.model.user.User;
import org.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private  UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        UserDTO userDTO = userMapper.toUserDto(user);

        return new Session(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())),
                userDTO
        );
    }

    public UserDTO registerUser(RegistrationRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST,"Username already taken.");
        }

        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already registered.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);

        try {
            User savedUser = userRepository.save(user);
            return userMapper.toUserDto(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("User registration failed: " + e.getMessage(), e);
        }
    }

    public List<UserDTO> getAllUsersDTO() {
        return userMapper.tuUserListDto(userRepository.findAll());
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public UserDTO getUserDTOByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        return userMapper.toUserDto(user.get());
    }

    public User getUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found: " + username);
        }

        return user.get();
    }

    public UserDTO getUserDTOByID(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }

        return userMapper.toUserDto(user.get());
    }

    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) {
            throw new IllegalArgumentException("User not found.");
        }

        return user.get();
    }

    @Transactional
    public UserDTO updateOwnUser(Long id, UserUpdateRequest request) {
        if (!JwtAuthenticationFilter.AuthenticatedUser().getUserDTO().getId().equals(id) ) {
            throw new IllegalArgumentException("Cannot modify other users");
        }

        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already registered.");
        }

        user.setEmail(request.getEmail());
        user.setStreak(request.getStreak());
        user.setLevel(request.getLevel());

        User saved=userRepository.save(user);

        return userMapper.toUserDto(saved);
    }

    @Transactional
    public UserDTO createNewUser(RegistrationRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())){
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST,"Username already taken.");
        }

        if (userRepository.existsByEmail(request.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Email already registered.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(Role.USER);

        try {
            User savedUser = userRepository.save(user);
            return userMapper.toUserDto(savedUser);
        } catch (Exception e) {
            throw new RuntimeException("User creation failed: " + e.getMessage(), e);
        }
    }

    @Transactional
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
