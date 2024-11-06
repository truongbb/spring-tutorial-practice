package com.github.truongbb.jwtrefreshtoken.repository;


import com.github.truongbb.jwtrefreshtoken.entity.User;
import com.github.truongbb.jwtrefreshtoken.statics.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByUsernameAndStatus(String username, UserStatus status);

}