package com.hramyko.finalapp.service.security.jwt;

import com.hramyko.finalapp.persistence.entity.Status;
import com.hramyko.finalapp.persistence.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE),
                user.getRole().getAuthorities()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<SimpleGrantedAuthority> role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }
}