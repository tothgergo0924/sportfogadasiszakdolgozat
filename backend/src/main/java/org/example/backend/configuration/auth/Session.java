package org.example.backend.configuration.auth;

import lombok.Getter;
import org.example.backend.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class Session implements UserDetails {
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    @Getter
    private final UserDTO userDTO;

    public Session(String username, String password,
                   Collection<? extends GrantedAuthority> authorities,
                   UserDTO userDTO) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.userDTO = userDTO;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
