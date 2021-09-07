package com.k4rnaj1k.bestcafe;

import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RoleDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.dto.order.DishOrderDTO;
import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.service.MenuService;
import com.k4rnaj1k.bestcafe.service.OrderService;
import com.k4rnaj1k.bestcafe.service.UserService;
import java.util.Collections;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceTests {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    private final User admin;
    private final User user1;
    private final User user2;

    public OrderServiceTests() {
        this.setUpRoles();
        this.admin = createAdmin();
        this.user1 = createUser("user", "user@email.com", "user");
        this.user2 = createUser("user2", "user2@email.com", "user2");
        this.menuService.createIngredient(new IngredientDTO("potato"));
        this.menuService.createDish(new DishPostDTO("French fries", List.of(1L), 30.5));
    }

    private void setUpRoles() {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleRepository.saveAll(List.of(adminRole, userRole));
    }

    private User createAdmin() {
        User admin = createUser("admin", "admin@admin.com", "admin");
        Assertions.assertNotNull(admin);

        UserRoleUpdateDTO updateDTO = new UserRoleUpdateDTO();
        updateDTO.setUsername("admin");
        updateDTO.setAddRoles(List.of(new RoleDTO("ROLE_ADMIN")));
        return userService.updateUserRoles(updateDTO);
    }

    private User createUser(String username, String email, String password){
        RegistrationRequestDTO requestDTO = new RegistrationRequestDTO(username, email, password);
        return userService.createUser(requestDTO);
    }

    @Test
    @Order(1)
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDishes(List.of(new DishOrderDTO(1L, 1L, Collections.emptyList())));
        orderDTO.setDishes(Collections.emptyList());
        orderService.createOrder(orderDTO, user1);
        Assertions.assertEquals(1, orderService.getOrders(user1).size());
        Assertions.assertEquals(0, orderService.getOrders(user2).size());
        Assertions.assertEquals(1, orderService.getOrders(admin).size());
    }
}
