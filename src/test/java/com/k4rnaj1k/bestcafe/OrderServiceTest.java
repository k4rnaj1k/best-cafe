package com.k4rnaj1k.bestcafe;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.menuitem.IngredientDTO;
import com.k4rnaj1k.bestcafe.dto.order.DishOrderDTO;
import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.model.menu.Dish;
import com.k4rnaj1k.bestcafe.model.menu.Ingredient;
import com.k4rnaj1k.bestcafe.service.MenuService;
import com.k4rnaj1k.bestcafe.service.OrderService;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(UserServiceConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTest {

    private final OrderService orderService;

    private final User admin;
    private final User user1;
    private final User user2;
    private final MenuService menuService;

    @Autowired
    public OrderServiceTest(OrderService orderService, UserService testUserService, MenuService menuService) {
        this.orderService = orderService;
        this.menuService = menuService;
        this.admin = testUserService.findByUsername("admin");
        this.user1 = testUserService.findByUsername("user1");
        this.user2 = testUserService.findByUsername("user2");
    }

    private Long dishId;
    private Long ingredientId;

    @BeforeAll
    public void setUp() {
        Ingredient ingredient = menuService.createIngredient(new IngredientDTO("potato"));
        Dish dish = menuService.createDish(new DishPostDTO("French fries", List.of(ingredient.getId()), 30.5));
        dishId = dish.getId();
        ingredientId = ingredient.getId();
    }

    @Test
    @Order(1)
    @DisplayName("Create order.")
    public void createOrder() {
        DishOrderDTO dishOrderDTO = new DishOrderDTO(dishId, 1L, Collections.emptyList());
        OrderDTO orderDTO = new OrderDTO(List.of(dishOrderDTO), Collections.emptyList());
        orderService.createOrder(orderDTO, user1);
        Assertions.assertEquals(1, orderService.getOrders(user1).size());
        Assertions.assertEquals(0, orderService.getOrders(user2).size());
        Assertions.assertEquals(1, orderService.getOrders(admin).size());
    }

    @Test
    @Order(2)
    @DisplayName("Create order with too many excluded.")
    public void createOrderWithTooManyExcluded() {
        DishOrderDTO dishOrderDTO = new DishOrderDTO(dishId, 1L, List.of(ingredientId));
        OrderDTO orderDTO = new OrderDTO(List.of(dishOrderDTO), Collections.emptyList());
        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.createOrder(orderDTO, user1));
        Assertions.assertEquals(1, orderService.getOrders(user1).size());
    }

    @Test
    @Order(3)
    @DisplayName("Update order.")
    public void updateOrder() {
        DishOrderDTO dishOrder = new DishOrderDTO(dishId, 1L, Collections.emptyList());
        OrderDTO orderDTO = new OrderDTO(List.of(dishOrder), Collections.emptyList());
        Assertions.assertThrows(AuthorizationServiceException.class, () -> orderService.updateOrder(orderDTO, 1L, user2));
        Assertions.assertDoesNotThrow(() -> orderService.updateOrder(orderDTO, 1L, user1));
    }

    @Test
    @Order(4)
    @DisplayName("Update order's status")
    public void updateOrderStatus() {
        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.updateOrderStatus(1L, com.k4rnaj1k.bestcafe.model.order.Order.OrderStatus.SENT));
        Assertions.assertDoesNotThrow(() -> orderService.updateOrderStatus(1L, com.k4rnaj1k.bestcafe.model.order.Order.OrderStatus.ACCEPTED));

        OrderDTO orderDTO = new OrderDTO(Collections.emptyList(), Collections.emptyList());
        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.updateOrder(orderDTO, 1L, admin));


        Assertions.assertDoesNotThrow(() -> orderService.updateOrderStatus(1L, com.k4rnaj1k.bestcafe.model.order.Order.OrderStatus.DONE));
    }
}
