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
                //user controller
                .antMatchers(HttpMethod.PUT, Routes.USERS).hasRole("USER")
                .antMatchers(HttpMethod.DELETE, Routes.USERS).hasRole("ADMIN")
                .antMatchers(Routes.REGISTER).anonymous()
                .antMatchers(Routes.LOGIN).anonymous()

                //order controller
                .antMatchers(HttpMethod.PUT, Routes.ORDERS+"/{\\d+}").hasRole("USER")
                .antMatchers(HttpMethod.PUT, Routes.ORDERS_STATUS).hasAnyRole("COOK", "ADMIN")
                .antMatchers(Routes.ORDERS).authenticated()

                //item controller
                .antMatchers(Routes.INGREDIENTS+"/{\\d+}").hasAnyRole("ADMIN", "COOK")
                .antMatchers(Routes.DISHES+"{\\d+}").hasAnyRole("ADMIN", "COOK")
                .antMatchers(HttpMethod.GET, Routes.DISHES, Routes.DRINKS).anonymous()
                .antMatchers(HttpMethod.POST, Routes.DISHES, Routes.INGREDIENTS, Routes.DRINKS).hasAnyRole("ADMIN", "COOK")

                //admin controller
                .antMatchers(Routes.USER_ROLES, Routes.USERS+"{\\d+}", Routes.ADMIN).hasRole("ADMIN")

                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));

    }
}
