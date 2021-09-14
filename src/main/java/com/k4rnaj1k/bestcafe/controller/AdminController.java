package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(Routes.USERS + "/roles")
    public User updateUserRoles(@RequestBody @Valid UserRoleUpdateDTO updateDTO) {
        return userService.updateUserRoles(updateDTO);
    }

    @DeleteMapping(Routes.USERS + "/{id}")
    public void deleteUser(@PathVariable(name = "id") Long userId) {
        userService.deleteById(userId);
    }

    @GetMapping(Routes.ADMIN)
    public List<User> getAll() {
        return userService.getAll();
    }
}
