package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.UserDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@OpenAPIDefinition(tags=@Tag(name="admin"))
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(Routes.USERS + "/roles")
    @Tags(@Tag(name="admin"))
    public UserDTO updateUserRoles(@RequestBody @Valid UserRoleUpdateDTO updateDTO) {
        return UserDTO.fromUser(userService.updateUserRoles(updateDTO));
    }

    @Tags(@Tag(name="admin"))
    @DeleteMapping(Routes.USERS + "/{name}")
    public void deleteUser(@PathVariable(name = "name") String username) {
        userService.deleteByUsername(username);
    }

    @Tags(@Tag(name="admin"))
    @GetMapping(Routes.ADMIN)
    public List<UserDTO> getAll() {
        List<User> users = userService.getAll();
        List<UserDTO> userDTOS = new ArrayList<>();
        users.forEach(user -> userDTOS.add(new UserDTO(user.getUsername(), user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))));
        return userDTOS;
    }
}
