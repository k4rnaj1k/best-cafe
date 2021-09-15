package com.k4rnaj1k.bestcafe.security;

import com.k4rnaj1k.bestcafe.model.auth.User;
import com.k4rnaj1k.bestcafe.security.jwt.JwtUserFactory;
import com.k4rnaj1k.bestcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUsername(s);

        return JwtUserFactory.create(user);
    }
}
