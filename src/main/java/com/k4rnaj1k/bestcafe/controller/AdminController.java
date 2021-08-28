package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.dto.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Routes.API_URL)
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping(Routes.ADMIN)
    public User updateUser(@RequestBody UserRoleUpdateDTO updateDTO){
        return userService.updateUserRoles(updateDTO);
    }
}
