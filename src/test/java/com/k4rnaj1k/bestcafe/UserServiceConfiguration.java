package com.k4rnaj1k.bestcafe;

import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RoleDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.Collections;
import java.util.List;

@TestConfiguration
public class UserServiceConfiguration {

    @Autowired
    private RoleRepository roleRepository;

    @Bean
    public UserService testUserService(UserService userService){
        setUpRoles();
        userService.createUser(new RegistrationRequestDTO("test-admin", "test-admin@admin.com","admin"));
        userService.createUser(new RegistrationRequestDTO("test-user1", "test-user1@user.com","user"));
        userService.createUser(new RegistrationRequestDTO("test-user2", "test-user2@user.com","user"));

        userService.updateUserRoles(new UserRoleUpdateDTO("test-admin", List.of(new RoleDTO("ROLE_ADMIN")), Collections.emptyList()));
        return userService;
    }

    public void setUpRoles() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role cookRole = new Role();
        cookRole.setName("ROLE_COOK");

        Assertions.assertNotNull(roleRepository.saveAll(List.of(adminRole, userRole, cookRole)));
    }
}
