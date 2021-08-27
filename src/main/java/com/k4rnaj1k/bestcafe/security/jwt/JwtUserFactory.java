package com.k4rnaj1k.bestcafe.security.jwt;

import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.Status;
import com.k4rnaj1k.bestcafe.model.auth.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {
    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(user.getStatus().equals(Status.ACTIVE), user.getId(), user.getUsername(), user.getFirstName(), user.getLastName(), user.getPassword(), user.getEmail(), user.getUpdatedAt(), fromRoles(user.getRoles()));
    }

    private static List<GrantedAuthority> fromRoles(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
