package com.github.truongbb.basicauthenticationajax.repository;


import com.github.truongbb.basicauthenticationajax.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}