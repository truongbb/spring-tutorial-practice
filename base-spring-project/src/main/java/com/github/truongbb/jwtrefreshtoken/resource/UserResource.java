package com.github.truongbb.jwtrefreshtoken.resource;

import com.github.truongbb.jwtrefreshtoken.exception.ExistedUserException;
import com.github.truongbb.jwtrefreshtoken.exception.ObjectNotFoundException;
import com.github.truongbb.jwtrefreshtoken.exception.PasswordNotMatchedException;
import com.github.truongbb.jwtrefreshtoken.model.request.CreateUserRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.PasswordChangingRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.UserSearchRequest;
import com.github.truongbb.jwtrefreshtoken.model.response.CommonSearchResponse;
import com.github.truongbb.jwtrefreshtoken.model.response.UserResponse;
import com.github.truongbb.jwtrefreshtoken.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserResource {

    UserService userService;

    @GetMapping
    public CommonSearchResponse<?> search(UserSearchRequest request) {
        return userService.searchUser(request);
    }

    @GetMapping("/{id}")
    public UserResponse getDetail(@PathVariable Long id) throws ObjectNotFoundException {
        return userService.getDetail(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateUserRequest request) throws ExistedUserException {
        UserResponse userResponse = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(userResponse);
    }

}
