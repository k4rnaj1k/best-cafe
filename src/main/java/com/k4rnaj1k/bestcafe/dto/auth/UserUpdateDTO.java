package com.k4rnaj1k.bestcafe.dto.auth;

public record UserUpdateDTO(
        String firstName,
        String lastName,
        String password,
        String newPassword,
        String email) {
}
