package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.auth.UserUpdateDTO;
import com.k4rnaj1k.bestcafe.dto.auth.AuthenticationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.DeleteUserRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserResponceDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(Routes.USERS)
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("register")
    public UserResponceDTO register(@RequestBody RegistrationRequestDTO requestDTO) {
        var user = userService.createUser(requestDTO);
        return userService.getToken(user);
    }

    @PostMapping("login")
    public UserResponceDTO login(@RequestBody AuthenticationRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));
        User user = userService.findByUsername(username);
        return userService.getToken(user);
    }

    @PutMapping
    public User updateUserDetails(@RequestBody UserUpdateDTO userUpdateDTO, Principal principal){
        return userService.updateUser(userUpdateDTO, principal.getName());
    }

    @DeleteMapping
    public void deleteUser(@RequestBody DeleteUserRequestDTO userRequestDTO){
        userService.deleteUser(userRequestDTO);
    }
}

