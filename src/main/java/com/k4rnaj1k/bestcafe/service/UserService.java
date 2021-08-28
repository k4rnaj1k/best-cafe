package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.exception.AuthorizationException;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.Status;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.repository.auth.UserRepository;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));
    }

    User register(User user) {
        Role role = roleRepository.findByName("ROLE_USER");

        user.setRoles(List.of(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        return userRepository.save(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> AuthorizationException.userWithEmailNotFound(email));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> AuthorizationException.userWithIdNotFound(id));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User createUser(RegistrationRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.getEmail()) || userRepository.existsByUsername(requestDTO.getUsername())) {
            throw new AuthenticationServiceException("user already exists.");
        }
        System.out.println(roleRepository.findByName("USER"));
        System.out.println(roleRepository.findByName("ROLE_USER"));
//        User user = User.fromRequestDto(requestDTO, List.of(roleRepository.findByName("ROLE_USER")));
        User user = User.fromRequestDto(requestDTO, List.of(roleRepository.findByName("ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Instant now = Instant.now();
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        return userRepository.save(user);
    }
}
