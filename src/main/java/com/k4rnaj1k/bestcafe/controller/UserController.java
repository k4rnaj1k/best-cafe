package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.auth.UserUpdateDTO;
import com.k4rnaj1k.bestcafe.dto.auth.AuthenticationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.DeleteUserRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserTokenDTO;
import com.k4rnaj1k.bestcafe.dto.user.UserResponceDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
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
    public UserTokenDTO register(@RequestBody RegistrationRequestDTO requestDTO) {
        var user = userService.createUser(requestDTO);
        return userService.getToken(user);
    }

    @PostMapping("login")
    public UserTokenDTO login(@RequestBody AuthenticationRequestDTO requestDTO) {
        String username = requestDTO.username();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.password()));
        User user = userService.findByUsername(username);
        return userService.getToken(user);
    }

    @PutMapping
    public UserResponceDTO updateUserDetails(@RequestBody UserUpdateDTO userUpdateDTO, Principal principal){
        User updatedUser = userService.updateUser(userUpdateDTO, principal.getName());
        return UserResponceDTO.fromUser(updatedUser);
    }

    @DeleteMapping
    public void deleteUser(@RequestBody DeleteUserRequestDTO userRequestDTO){
        userService.deleteUser(userRequestDTO);
    }
}

