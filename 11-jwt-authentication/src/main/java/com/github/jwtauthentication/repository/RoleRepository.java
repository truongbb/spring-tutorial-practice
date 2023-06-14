package com.github.jwtauthentication.repository;

import com.github.jwtauthentication.entity.Role;
import com.github.jwtauthentication.statics.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);

}
