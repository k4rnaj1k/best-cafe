package com.k4rnaj1k.bestcafe.dto.auth;

import java.util.List;

public record UserRoleUpdateDTO(String username,
                                List<RoleDTO> addRoles,
                                List<RoleDTO> removeRoles) {
}
