package com.k4rnaj1k.bestcafe.dto.auth;

import javax.validation.constraints.NotBlank;

public record AuthenticationRequestDTO(
        @NotBlank(message = "username can't be blank")
        String username,
        @NotBlank(message = "password can't be blank")
        String password) {
}
