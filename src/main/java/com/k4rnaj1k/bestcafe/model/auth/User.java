package com.k4rnaj1k.bestcafe.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.k4rnaj1k.bestcafe.dto.auth.RegistrationRequestDTO;
import com.k4rnaj1k.bestcafe.model.order.Order;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    public static User fromRequestDto(RegistrationRequestDTO requestDTO, List<Role> roles) {
        User result = new User();
        result.setUsername(requestDTO.getUsername());
        result.setRoles(roles);
        result.setPassword(requestDTO.getPassword());
        result.setEmail(requestDTO.getEmail());
        return result;
    }
}
