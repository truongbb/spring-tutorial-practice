package org.example.jwtauthenticationspring3.repository;


import org.example.jwtauthenticationspring3.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}