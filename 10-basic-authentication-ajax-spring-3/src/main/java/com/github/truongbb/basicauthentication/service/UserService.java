package com.github.truongbb.basicauthentication.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.truongbb.basicauthentication.entity.Role;
import com.github.truongbb.basicauthentication.entity.User;
import com.github.truongbb.basicauthentication.model.request.UserRequest;
import com.github.truongbb.basicauthentication.model.response.UserResponse;
import com.github.truongbb.basicauthentication.repository.RoleRepository;
import com.github.truongbb.basicauthentication.repository.UserRepository;
import com.github.truongbb.basicauthentication.statics.Roles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    ObjectMapper objectMapper;

    RoleRepository roleRepository;

    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();
        if (!CollectionUtils.isEmpty(users)) {
            return users.stream().map(u -> objectMapper.convertValue(u, UserResponse.class)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public UserResponse getDetail(Long id) throws ClassNotFoundException {
        return userRepository.findById(id).map(u -> objectMapper.convertValue(u, UserResponse.class)).orElseThrow(ClassNotFoundException::new);
    }

    public UserResponse create(UserRequest request) {
        Optional<Role> roleOptional = roleRepository.findByName(Roles.USER.name());

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt the password
        Set<Role> roles = new HashSet<>();
        roles.add(roleOptional.get());
        user.setRoles(roles);
        userRepository.save(user);

        return objectMapper.convertValue(user, UserResponse.class);
    }

}
