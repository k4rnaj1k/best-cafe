package com.k4rnaj1k.bestcafe.dto;

import lombok.Data;

@Data
public class RegistrationRequestDTO {
    private String username;
    private String email;
    private String password;
}
