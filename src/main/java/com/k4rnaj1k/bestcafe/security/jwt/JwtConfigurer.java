package com.k4rnaj1k.bestcafe.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider provider;

    public JwtConfigurer(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public void configure(HttpSecurity security) throws Exception {
        security.addFilterBefore(new JwtTokenFilter(provider), UsernamePasswordAuthenticationFilter.class);
    }
}
