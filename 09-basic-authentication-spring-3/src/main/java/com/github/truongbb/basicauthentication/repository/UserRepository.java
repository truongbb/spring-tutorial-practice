package com.github.truongbb.basicauthentication.repository;


import com.github.truongbb.basicauthentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}