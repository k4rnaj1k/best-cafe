package com.k4rnaj1k.bestcafe;

import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RoleDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.junit.jupiter.api.Assertions;

import java.util.Collections;
import java.util.List;

public class UserUtils {
    public static void setUpUsers(UserService userService){
        userService.createUser(new RegistrationRequestDTO("admin", "admin@admin.com", "admin"));
        userService.createUser(new RegistrationRequestDTO("user1", "user1@user.com", "user"));
        userService.createUser(new RegistrationRequestDTO("user2", "user2@user.com", "user"));

        userService.updateUserRoles(new UserRoleUpdateDTO("admin", List.of(new RoleDTO("ROLE_ADMIN")), Collections.emptyList()));
    }

    public static void setUpRoles(RoleRepository roleRepository) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role cookRole = new Role();
        cookRole.setName("ROLE_COOK");

        Assertions.assertNotNull(roleRepository.saveAll(List.of(adminRole, userRole, cookRole)));
    }
}
