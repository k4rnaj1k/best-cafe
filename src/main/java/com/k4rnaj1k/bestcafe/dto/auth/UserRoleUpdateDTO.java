package com.k4rnaj1k.bestcafe.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public record UserRoleUpdateDTO(@NotBlank String username,
                                @NotNull List<RoleDTO> addRoles,
                                @NotNull List<RoleDTO> removeRoles) {
}
