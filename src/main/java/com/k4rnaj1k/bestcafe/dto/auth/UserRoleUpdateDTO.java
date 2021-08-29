package com.k4rnaj1k.bestcafe.dto.auth;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleUpdateDTO {
    private String username;
    private List<RoleDTO> addRoles;
    private List<RoleDTO> removeRoles;
}
