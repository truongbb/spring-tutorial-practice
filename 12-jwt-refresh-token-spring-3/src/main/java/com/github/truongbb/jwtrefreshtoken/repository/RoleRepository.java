package com.github.truongbb.jwtrefreshtoken.repository;

import com.github.truongbb.jwtrefreshtoken.entity.Role;
import com.github.truongbb.jwtrefreshtoken.statics.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(Roles name);

}
