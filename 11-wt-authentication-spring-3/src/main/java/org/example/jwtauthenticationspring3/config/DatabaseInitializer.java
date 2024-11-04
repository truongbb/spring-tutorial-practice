package org.example.jwtauthenticationspring3.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.jwtauthenticationspring3.entity.Role;
import org.example.jwtauthenticationspring3.entity.User;
import org.example.jwtauthenticationspring3.repository.RoleRepository;
import org.example.jwtauthenticationspring3.repository.UserRepository;
import org.example.jwtauthenticationspring3.statics.Roles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseInitializer implements CommandLineRunner {

    UserRepository userRepository;

    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Role userRole = Role.builder().name(Roles.USER).build();
        Role adminRole = Role.builder().name(Roles.ADMIN).build();
        roleRepository.save(userRole);
        roleRepository.save(adminRole);

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("admin123")); // Encrypt the password
        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);
    }

}
