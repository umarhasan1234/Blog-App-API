package com.myblog.blogapp.reprository;

import com.myblog.blogapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findbyUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    Boolean exitbyUsername(String username);
    Boolean exitByEmail(String email);
}
