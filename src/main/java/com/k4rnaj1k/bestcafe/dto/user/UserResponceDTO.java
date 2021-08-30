package com.k4rnaj1k.bestcafe.dto.user;

import com.k4rnaj1k.bestcafe.model.auth.User;
import lombok.Data;

@Data
public class UserResponceDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;

    public static UserResponceDTO fromUser(User user) {
        UserResponceDTO userResponceDTO = new UserResponceDTO();
        userResponceDTO.setUsername(user.getUsername());
        userResponceDTO.setEmail(user.getEmail());
        userResponceDTO.setFirstName(user.getFirstName());
        userResponceDTO.setLastName(user.getLastName());
        return userResponceDTO;
    }
}
