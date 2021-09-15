package com.k4rnaj1k.bestcafe.dto.auth;

import javax.validation.constraints.NotBlank;

public record DeleteUserRequestDTO(@NotBlank String username,
                                   @NotBlank String password) {
}
