package com.k4rnaj1k.bestcafe.dto.auth;

import lombok.Data;

@Data
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String newPassword;
    private String email;
}
