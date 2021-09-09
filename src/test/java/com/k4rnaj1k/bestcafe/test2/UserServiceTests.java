package com.k4rnaj1k.bestcafe.test2;

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
import java.util.List;

@SpringBootTest
@DisplayName("2Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @Order(1)
    @DisplayName("Set up roles.")
    public void setUpRoles() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");

        Role cookRole = new Role();
        cookRole.setName("ROLE_COOK");

        Assertions.assertNotNull(roleRepository.saveAll(List.of(adminRole, userRole, cookRole)));
    }

    @Test
    @Order(2)
    @DisplayName("Create users.")
    public void createUsers() {
        Assertions.assertNotNull(createUser("user1", "user@email.com", "user"));
        Assertions.assertNotNull(createUser("user2", "user2@email.com", "user2"));
    }

    private User createUser(String username, String email, String password) {
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(username, email, password);
        return userService.createUser(requestDTO);
    }

    @Test
    @Order(3)
    @DisplayName("Create admin.")
    public void createAdmin() {
        User admin = createUser("admin", "admin@admin.com", "admin");
        Assertions.assertNotNull(admin);

        UserRoleUpdateDTO updateDTO = new UserRoleUpdateDTO("admin", List.of(new RoleDTO("ROLE_ADMIN")), Collections.emptyList());
        admin = userService.updateUserRoles(updateDTO);
        Assertions.assertNotNull(admin);
        boolean result = admin.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"));

        Assertions.assertTrue(result);
    }
}
