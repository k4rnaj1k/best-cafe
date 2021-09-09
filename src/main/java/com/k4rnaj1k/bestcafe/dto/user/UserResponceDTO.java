package com.k4rnaj1k.bestcafe.dto.user;

import com.k4rnaj1k.bestcafe.model.auth.User;

public record UserResponceDTO(
        String username,
        String firstName,
        String lastName,
        String email) {

    public static UserResponceDTO fromUser(User user) {
        return new UserResponceDTO(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName());
    }
}
