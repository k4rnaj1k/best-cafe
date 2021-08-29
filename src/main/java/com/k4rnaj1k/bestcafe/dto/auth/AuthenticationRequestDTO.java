package com.k4rnaj1k.bestcafe.dto.auth;

import lombok.Data;

@Data
public class AuthenticationRequestDTO {
    private String username;
    private String password;
}
