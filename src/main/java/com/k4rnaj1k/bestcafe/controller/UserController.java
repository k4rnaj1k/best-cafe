package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.dto.AuthenticationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.DeleteUserRequestDTO;
import com.k4rnaj1k.bestcafe.dto.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.UserResponceDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.security.jwt.JwtTokenProvider;
import com.k4rnaj1k.bestcafe.security.jwt.JwtUser;
import com.k4rnaj1k.bestcafe.security.jwt.JwtUserFactory;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @PostMapping
    public UserResponceDTO register(@RequestBody RegistrationRequestDTO requestDTO) {
        var user = userService.createUser(requestDTO);
        return userService.getResponce(user);
    }

    @GetMapping
    public UserResponceDTO login(@RequestBody AuthenticationRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));
        User user = userService.findByUsername(username);
        return userService.getResponce(user);
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

