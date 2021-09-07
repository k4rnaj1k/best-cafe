package com.k4rnaj1k.bestcafe.dto.auth;

public record RegistrationRequestDTO
        (String username,
         String email,
         String password) {
}
