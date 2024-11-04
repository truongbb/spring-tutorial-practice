package org.example.jwtauthenticationspring3.repository;

import org.example.jwtauthenticationspring3.entity.Role;
import org.example.jwtauthenticationspring3.statics.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);

}
