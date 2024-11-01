package com.github.truongbb.basicauthentication.controller;

import com.github.truongbb.basicauthentication.model.request.UserRequest;
import com.github.truongbb.basicauthentication.model.response.UserResponse;
import com.github.truongbb.basicauthentication.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponse getDetail(@PathVariable Long id) throws ClassNotFoundException {
        return userService.getDetail(id);
    }

    @PostMapping
    public UserResponse create(@RequestBody @Valid UserRequest request) {
        return userService.create(request);
    }

}
