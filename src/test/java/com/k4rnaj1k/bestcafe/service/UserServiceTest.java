package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.UserUtils;
import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RoleDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext
public class UserServiceTest {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeAll
    public void setUserService() {
        UserUtils.setUpRoles(roleRepository);
    }


    @Test
    @Order(1)
    public void createUsers() {
        System.out.println(userService.getRoles());
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
    @Order(2)
    public void updateAdmin() {
        User admin = userService.findByUsername("admin");
        Assertions.assertNotNull(admin);
        UserRoleUpdateDTO updateDTO = new UserRoleUpdateDTO(admin.getUsername(), List.of(new RoleDTO("ROLE_ADMIN")), Collections.emptyList());
        Assertions.assertNotNull(userService.updateUserRoles(updateDTO));
        Assertions.assertTrue(() -> userService.findByUsername(admin.getUsername()).getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
    }

    @Test
    @Order(3)
    public void createDuplicateUser() {
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO("admin", "admin@email.com", "admin");
        Assertions.assertThrows(AuthenticationServiceException.class, () -> userService.createUser(requestDTO));
    }
}
