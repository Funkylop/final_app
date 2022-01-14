package com.hramyko.finalapp.security.UserDetailsServiceImpl;

import com.hramyko.finalapp.persistence.entity.Status;
import com.hramyko.finalapp.persistence.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private boolean enabled;
    private List<SimpleGrantedAuthority> authorities;

    public CustomUserDetails(String email, String password, boolean enabled, List<SimpleGrantedAuthority> authorities) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public static UserDetails fromUser(String email, String password, boolean b1,
                                       boolean b2, boolean b3, boolean b4, Set<SimpleGrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(
                email, password, b1, b2, b3, b4, authorities
        );
    }
}
