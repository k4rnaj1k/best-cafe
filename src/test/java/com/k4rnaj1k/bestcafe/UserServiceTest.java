package com.k4rnaj1k.bestcafe;

import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RoleDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    private UserService userService;
    private RoleRepository roleRepository;


    @Autowired
    public UserServiceTest(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @Test
    @DisplayName("Create roles.")
    @Order(1)
    public void createRoles() {
        String[] roleNames = {"ROLE_USER", "ROLE_ADMIN", "ROLE_COOK"};
        for (String roleName :
                roleNames) {
            Role role = new Role();
            role.setName(roleName);
            Assertions.assertNotNull(roleRepository.save(role));
        }
    }

    @Test
    @Order(2)
    public void createUsers() {
        Map<String, String> userData = new HashMap<>() {{
            put("user1", "user");
            put("user2", "user");
            put("admin", "admin");
        }};

        userData.forEach((name, password) -> {
            RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(name, name + "@" + password + ".com", password);
            Assertions.assertNotNull(userService.createUser(requestDTO));
        });
    }

    @Test
    @Order(3)
    public void updateAdmin() {
        User admin = userService.findByUsername("admin");
        Assertions.assertNotNull(admin);
        UserRoleUpdateDTO updateDTO = new UserRoleUpdateDTO(admin.getUsername(), List.of(new RoleDTO("ROLE_ADMIN")), Collections.emptyList());
        Assertions.assertNotNull(userService.updateUserRoles(updateDTO));
        Assertions.assertTrue(() -> userService.findByUsername(admin.getUsername()).getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
    }
}
