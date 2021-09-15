package com.k4rnaj1k.bestcafe.dto.auth;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record RegistrationRequestDTO(@NotBlank String username,
                                     @Email String email,
                                     @NotBlank String password) {
}
