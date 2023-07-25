package com.github.truongbb.jwtrefreshtoken.controller;

import com.github.truongbb.jwtrefreshtoken.exception.ExistedUserException;
import com.github.truongbb.jwtrefreshtoken.model.request.CreateUserRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.UserSearchRequest;
import com.github.truongbb.jwtrefreshtoken.model.response.CommonResponse;
import com.github.truongbb.jwtrefreshtoken.model.response.UserResponse;
import com.github.truongbb.jwtrefreshtoken.model.response.UserSearchResponse;
import com.github.truongbb.jwtrefreshtoken.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserService userService;

//    @GetMapping
//    public List<UserResponse> getAll() {
//        return userService.getAll();
//    }

    @GetMapping("/{id}")
    public UserResponse getDetail(@PathVariable Long id) throws ClassNotFoundException {
        return userService.getDetail(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateUserRequest request) {
        try {
            userService.createUser(request);
            return ResponseEntity.ok(null);
        } catch (ExistedUserException ex) {
            return new ResponseEntity<>("username đã tồn tại", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public CommonResponse<?> search(UserSearchRequest request) {
        return userService.searchUser(request);
    }

    @GetMapping("/v2")
    public ModelAndView search1(UserSearchRequest request) {
        ModelAndView modelAndView  = new ModelAndView("/users");
        modelAndView.addObject("userData", userService.searchUser(request));
        return modelAndView;
    }

}
