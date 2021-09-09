package com.k4rnaj1k.bestcafe.test3;

import com.k4rnaj1k.bestcafe.dto.menuitem.DishPostDTO;
import com.k4rnaj1k.bestcafe.dto.order.DishOrderDTO;
import com.k4rnaj1k.bestcafe.dto.order.OrderDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.MenuService;
import com.k4rnaj1k.bestcafe.service.OrderService;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@Order(3)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrderServiceTests {

    private final OrderService orderService;
    private final MenuService menuService;

    private final User admin;
    private final User user1;
    private final User user2;

    @Autowired
    public OrderServiceTests(OrderService orderService, MenuService menuService, UserService userService) {
        this.orderService = orderService;
        this.menuService = menuService;
        this.admin = userService.findByUsername("admin");
        this.user1 = userService.findByUsername("user1");
        this.user2 = userService.findByUsername("user2");
    }


    private void createDish() {
        DishPostDTO dishPostDTO = new DishPostDTO("French fries", List.of(2L), 30.5);
        Assertions.assertNotNull(menuService.createDish(dishPostDTO));
    }

    @Test
    @Order(1)
    @DisplayName("Create order.")
    public void createOrder() {
        createDish();
        DishOrderDTO dishOrderDTO = new DishOrderDTO(2L, 1L, Collections.emptyList());
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
        DishOrderDTO dishOrderDTO = new DishOrderDTO(2L, 1L, List.of(1L));
        OrderDTO orderDTO = new OrderDTO(List.of(dishOrderDTO), Collections.emptyList());
        Assertions.assertThrows(ResponseStatusException.class, () -> orderService.createOrder(orderDTO, user1));
        Assertions.assertEquals(1, orderService.getOrders(user1).size());
    }

    @Test
    @Order(3)
    @DisplayName("Update order.")
    public void updateOrder() {
        DishOrderDTO dishOrder = new DishOrderDTO(2L, 1L, Collections.emptyList());
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
