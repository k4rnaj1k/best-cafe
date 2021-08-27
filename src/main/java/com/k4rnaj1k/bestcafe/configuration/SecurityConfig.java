package com.k4rnaj1k.bestcafe.configuration;

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
                .antMatchers("/login", "/register").permitAll()
                .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**", "*").hasRole("ADMIN")
                .antMatchers("/ingredients").hasRole("COOK")
                .antMatchers(HttpMethod.PUT, "/orders").hasRole("COOK")
                .antMatchers(HttpMethod.GET, "/orders").hasRole("COOK")
                .antMatchers(HttpMethod.GET, "/dishes").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/orders").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/orders").hasRole("USER")
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

    }
}