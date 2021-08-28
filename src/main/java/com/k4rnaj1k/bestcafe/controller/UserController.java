package com.k4rnaj1k.bestcafe.controller;

import com.k4rnaj1k.bestcafe.dto.AuthenticationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.UserResponceDTO;
import com.k4rnaj1k.bestcafe.dto.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.security.jwt.JwtTokenProvider;
import com.k4rnaj1k.bestcafe.security.jwt.JwtUser;
import com.k4rnaj1k.bestcafe.security.jwt.JwtUserFactory;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("register")
    public UserResponceDTO register(@RequestBody RegistrationRequestDTO requestDTO) {
        var user = userService.createUser(requestDTO);
        JwtUser jwtUser = JwtUserFactory.create(user);
        return new UserResponceDTO(jwtUser.getUsername(), jwtTokenProvider.createToken(jwtUser.getUsername(), user.getRoles()));
    }

    @PostMapping("login")
    public UserResponceDTO login(@RequestBody AuthenticationRequestDTO requestDTO) {
        String username = requestDTO.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDTO.getPassword()));
        User user = userService.findByUsername(username);
        String token = jwtTokenProvider.createToken(username, user.getRoles());
        return new UserResponceDTO(username, token);
    }
}

