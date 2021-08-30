package com.k4rnaj1k.bestcafe.configuration.security;

import com.k4rnaj1k.bestcafe.Routes;
import com.k4rnaj1k.bestcafe.security.jwt.JwtConfigurer;
import com.k4rnaj1k.bestcafe.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, Routes.DISHES, Routes.DRINKS).hasRole("USER")
                .antMatchers(HttpMethod.POST, Routes.ORDERS).hasRole("USER")
                .antMatchers(Routes.INGREDIENTS, Routes.ORDERS).hasRole("COOK")
                .antMatchers(HttpMethod.POST, Routes.DISHES).hasRole("COOK")
                .antMatchers(HttpMethod.PUT, Routes.ORDERS_STATUS).hasRole("COOK")
                .antMatchers(Routes.ADMIN).hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, Routes.USERS).hasRole("USER")
                .antMatchers(Routes.USERS).anonymous()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

    }
}
