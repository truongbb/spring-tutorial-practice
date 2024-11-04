package org.example.jwtauthenticationspring3.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.jwtauthenticationspring3.entity.Role;
import org.example.jwtauthenticationspring3.entity.User;
import org.example.jwtauthenticationspring3.exception.ObjectNotFoundException;
import org.example.jwtauthenticationspring3.model.request.RegistrationRequest;
import org.example.jwtauthenticationspring3.model.response.UserResponse;
import org.example.jwtauthenticationspring3.repository.RoleRepository;
import org.example.jwtauthenticationspring3.repository.UserRepository;
import org.example.jwtauthenticationspring3.statics.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    RoleRepository roleRepository;

    ObjectMapper objectMapper;

    public UserResponse registerUser(RegistrationRequest registrationRequest) throws ObjectNotFoundException {
        Role role = roleRepository.findByName(Roles.USER)
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find USER role"));
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        User user = User.builder()
                .username(registrationRequest.getUsername())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return objectMapper.convertValue(user, UserResponse.class);
    }


    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        if (!CollectionUtils.isEmpty(users)) {
            return users.stream()
                    .map(u -> objectMapper.convertValue(u, UserResponse.class))
                    .toList();
        }
        return Collections.emptyList();
    }

    public UserResponse getDetail(Long id) throws ObjectNotFoundException {
        return userRepository
                .findById(id)
                .map(u -> objectMapper.convertValue(u, UserResponse.class))
                .orElseThrow(() -> new ObjectNotFoundException("Cannot find user with id: " + id));
    }

}
