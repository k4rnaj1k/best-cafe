package com.k4rnaj1k.bestcafe.dto;

import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(String username, List<String> roles) {

    public static UserDTO fromUser(User updateUserRoles) {
        return new UserDTO(updateUserRoles.getUsername(), updateUserRoles.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
    }
}
