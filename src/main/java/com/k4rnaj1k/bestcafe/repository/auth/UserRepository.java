package com.k4rnaj1k.bestcafe.repository.auth;

import com.k4rnaj1k.bestcafe.model.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User getByUsername(String username);

    void deleteByUsername(String username);
}
