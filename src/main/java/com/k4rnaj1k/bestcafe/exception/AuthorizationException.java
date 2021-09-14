package com.k4rnaj1k.bestcafe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AuthorizationException {
    private AuthorizationException() {
    }

    public static ResponseStatusException userWithEmailNotFound(String email) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found.");
    }

    public static ResponseStatusException userWithIdNotFound(Long id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " not found.");
    }

    public static ResponseStatusException userWithUsernameNotFound(String username) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "User with username " + username + " not found.");
    }
}
