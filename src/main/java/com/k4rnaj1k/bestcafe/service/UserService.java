package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.auth.*;
import com.k4rnaj1k.bestcafe.exception.AuthorizationException;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.repository.auth.UserRepository;
import com.k4rnaj1k.bestcafe.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, @Lazy JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));
    }

    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> AuthorizationException.userWithEmailNotFound(email));
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> AuthorizationException.userWithIdNotFound(id));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User createUser(RegistrationRequestDTO requestDTO) {
        if (userRepository.existsByEmail(requestDTO.email()) || userRepository.existsByUsername(requestDTO.username())) {
            throw new AuthenticationServiceException("user already exists.");
        }
        User user = User.fromRequestDto(requestDTO, List.of(roleRepository.findByName("ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserRoles(UserRoleUpdateDTO updateDTO) {
        User user = userRepository.findByUsername(updateDTO.username())
                .orElseThrow(() -> new UsernameNotFoundException("Username " + updateDTO.username() + " not found"));
        List<Role> userRoles = user.getRoles();
        List<Role> addRoles = new ArrayList<>();
        copyRolesFromDTO(updateDTO, addRoles);
        userRoles.addAll(addRoles);

        List<Role> removeRoles = new ArrayList<>();
        copyRolesFromDTO(updateDTO, removeRoles);

        user.setRoles(userRoles);
        return userRepository.save(user);
    }

    private void copyRolesFromDTO(UserRoleUpdateDTO updateDTO, List<Role> copyTo) {
        updateDTO.addRoles().forEach(addRole -> copyTo.add(roleRepository.findByName(addRole.name())));
    }

    public User updateUser(UserUpdateDTO userUpdateDTO, String username) {
        User user = userRepository.getByUsername(username);
        if (userUpdateDTO.firstName() != null) {
            user.setFirstName(userUpdateDTO.firstName());
        }

        if (userUpdateDTO.lastName() != null) {
            user.setLastName(userUpdateDTO.lastName());
        }

        if (userUpdateDTO.newPassword() != null && passwordEncoder.matches(userUpdateDTO.password(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.newPassword()));
        }
        return user;
    }

    public void deleteUser(DeleteUserRequestDTO userRequestDTO) {
        User delete = userRepository.findByUsername(userRequestDTO.username())
                .orElseThrow(() -> AuthorizationException.userWithUsernameNotFound(userRequestDTO.username()));
        if (passwordEncoder.matches(userRequestDTO.password(), delete.getPassword())) {
            userRepository.delete(delete);
        }else{
            throw new AuthenticationServiceException("User password does not match.");
        }
    }

    public UserTokenDTO getToken(User user) {
        return new UserTokenDTO(user.getUsername(), jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
