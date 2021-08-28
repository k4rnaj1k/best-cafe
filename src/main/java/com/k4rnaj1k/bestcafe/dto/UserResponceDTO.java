package com.k4rnaj1k.bestcafe.dto;

import lombok.Data;

@Data
public class UserResponceDTO {
    private String username;
    private String token;

    public UserResponceDTO(String username, String token) {
        this.username = username;
        this.token = token;
    }
}
