package com.k4rnaj1k.bestcafe.dto.auth;

import javax.validation.constraints.NotBlank;

public record RoleDTO(@NotBlank String name) {
}
