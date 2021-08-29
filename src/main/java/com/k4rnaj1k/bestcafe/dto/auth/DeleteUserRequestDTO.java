package com.k4rnaj1k.bestcafe.dto.auth;

import lombok.Data;

@Data
public class DeleteUserRequestDTO {
    private String username;

    private String password;
}
