package com.k4rnaj1k.bestcafe.dto.auth;

import lombok.Data;

@Data
public class UserTokenDTO {
    private String username;
    private String token;

    public UserTokenDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
