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
            put("test-user1", "test-user");
            put("test-user2", "test-user");
            put("test-admin", "test-admin");
        }};

        userData.forEach((name, password) -> {
            RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(name, name + "@" + password + ".com", password);
            Assertions.assertNotNull(userService.createUser(requestDTO));
        });
    }

    @Test
    @Order(2)
    public void updateAdmin() {
        User admin = userService.findByUsername("test-admin");
        Assertions.assertNotNull(admin);
        UserRoleUpdateDTO updateDTO = new UserRoleUpdateDTO(admin.getUsername(), List.of(new RoleDTO("ROLE_ADMIN")), Collections.emptyList());
        Assertions.assertNotNull(userService.updateUserRoles(updateDTO));
        Assertions.assertTrue(() -> userService.findByUsername(admin.getUsername()).getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN")));
    }
}
