package com.k4rnaj1k.bestcafe.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthentificationException extends AuthenticationException {
    public JwtAuthentificationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtAuthentificationException(String msg) {
        super(msg);
    }
}
