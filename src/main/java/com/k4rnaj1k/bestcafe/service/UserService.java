package com.k4rnaj1k.bestcafe.service;

import com.k4rnaj1k.bestcafe.dto.auth.UserUpdateDTO;
import com.k4rnaj1k.bestcafe.dto.auth.DeleteUserRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserTokenDTO;
import com.k4rnaj1k.bestcafe.dto.auth.UserRoleUpdateDTO;
import com.k4rnaj1k.bestcafe.exception.AuthorizationException;
import com.k4rnaj1k.bestcafe.model.auth.Role;
import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.repository.auth.RoleRepository;
import com.k4rnaj1k.bestcafe.repository.auth.UserRepository;
import com.k4rnaj1k.bestcafe.security.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user " + username + " not found"));
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
        if (userRepository.existsByEmail(requestDTO.email()) || userRepository.existsByUsername(requestDTO.username())) {
            throw new AuthenticationServiceException("user already exists.");
        }
        User user = User.fromRequestDto(requestDTO, List.of(roleRepository.findByName("ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUserRoles(UserRoleUpdateDTO updateDTO) {
        User user = userRepository.findByUsername(updateDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username " + updateDTO.getUsername() + " not found"));
        List<Role> userRoles = user.getRoles();
        List<Role> addRoles = new ArrayList<>();
        copyRolesFromDTO(updateDTO, addRoles);
        userRoles.addAll(addRoles);

        List<Role> removeRoles = new ArrayList<>();
        copyRolesFromDTO(updateDTO, removeRoles);

        user.setRoles(userRoles);
        user.getRoles().forEach(role-> System.out.println(role.getName()));
        return userRepository.save(user);
    }

    private void copyRolesFromDTO(UserRoleUpdateDTO updateDTO, List<Role> copyTo) {
        updateDTO.getAddRoles().forEach(addRole -> copyTo.add(roleRepository.findByName(addRole.name())));
    }

    public User updateUser(UserUpdateDTO userUpdateDTO, String username) {
        User user = userRepository.getByUsername(username);
        if (userUpdateDTO.getFirstName() != null) {
            user.setFirstName(userUpdateDTO.getFirstName());
        }

        if (userUpdateDTO.getLastName() != null) {
            user.setLastName(userUpdateDTO.getLastName());
        }

        if (userUpdateDTO.getNewPassword() != null && passwordEncoder.matches(userUpdateDTO.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(userUpdateDTO.getNewPassword()));
        }
        return user;
    }

    public void deleteUser(DeleteUserRequestDTO userRequestDTO) {
        User delete = userRepository.findByUsername(userRequestDTO.getUsername())
                .orElseThrow(() -> AuthorizationException.userWithUsernameNotFound(userRequestDTO.getUsername()));
        if (passwordEncoder.matches(userRequestDTO.getPassword(), delete.getPassword())) {
            userRepository.delete(delete);
        }
    }

    public UserTokenDTO getToken(User user) {
        return new UserTokenDTO(user.getUsername(), jwtTokenProvider.createToken(user.getUsername(), user.getRoles()));
    }
}
