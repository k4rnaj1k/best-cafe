package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.dto.auth.*;
import com.k4rnaj1k.bestcafe.dto.user.UserResponceDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Tag(name = "users")
@Transactional
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public UserController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping(Routes.REGISTER)
    public UserTokenDTO register(@RequestBody @Valid RegistrationRequestDTO requestDTO) {
        var user = userService.createUser(requestDTO);
        return userService.getToken(user);
    }

    @PostMapping(Routes.LOGIN)
    public UserTokenDTO login(@RequestBody @Valid AuthenticationRequestDTO requestDTO) {
        String username = requestDTO.username();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.password()));
        User user = userService.findByUsername(username);
        return userService.getToken(user);
    }

    @PutMapping(Routes.USERS)
    public UserResponceDTO updateUserDetails(@RequestBody @Valid UserUpdateDTO userUpdateDTO, Principal principal) {
        User updatedUser = userService.updateUser(userUpdateDTO, principal.getName());
        return UserResponceDTO.fromUser(updatedUser);
    }

    @DeleteMapping(Routes.USERS)
    public void deleteUser(@RequestBody @Valid DeleteUserRequestDTO userRequestDTO) {
        userService.deleteUser(userRequestDTO);
    }
}

