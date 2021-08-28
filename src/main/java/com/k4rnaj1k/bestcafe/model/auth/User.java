package com.k4rnaj1k.bestcafe.model.auth;

import com.k4rnaj1k.bestcafe.dto.RegistrationRequestDTO;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User extends BaseEntity {
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String password;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public static User fromRequestDto(RegistrationRequestDTO requestDTO, List<Role> roles) {
        User result = new User();
        result.setUsername(requestDTO.getUsername());
        result.setStatus(Status.ACTIVE);
        result.setRoles(roles);
        result.setPassword(requestDTO.getPassword());
        result.setEmail(requestDTO.getEmail());
        return result;
    }
}
