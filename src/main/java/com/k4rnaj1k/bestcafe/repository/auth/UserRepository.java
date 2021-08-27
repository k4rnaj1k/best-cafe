package com.k4rnaj1k.bestcafe.repository.auth;

import com.k4rnaj1k.bestcafe.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);
}
