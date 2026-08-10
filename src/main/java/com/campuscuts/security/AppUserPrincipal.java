package com.campuscuts.security;

import com.campuscuts.entity.User;
import com.campuscuts.entity.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Wraps the domain User so controllers/guards can read live entity state (e.g. whether
 * the user currently has a Provider profile) via getUser(), instead of relying on
 * authorities baked in at login time.
 */
public class AppUserPrincipal implements UserDetails {

    private final User user;

    public AppUserPrincipal(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == Role.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
