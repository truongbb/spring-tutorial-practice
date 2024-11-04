package org.example.jwtauthenticationspring3.resource;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.example.jwtauthenticationspring3.entity.User;
import org.example.jwtauthenticationspring3.exception.ObjectNotFoundException;
import org.example.jwtauthenticationspring3.exception.UserExistedException;
import org.example.jwtauthenticationspring3.model.request.LoginRequest;
import org.example.jwtauthenticationspring3.model.request.RegistrationRequest;
import org.example.jwtauthenticationspring3.model.response.JwtResponse;
import org.example.jwtauthenticationspring3.model.response.UserResponse;
import org.example.jwtauthenticationspring3.repository.UserRepository;
import org.example.jwtauthenticationspring3.security.CustomUserDetails;
import org.example.jwtauthenticationspring3.security.JwtService;
import org.example.jwtauthenticationspring3.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authentications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationResource {

    JwtService jwtService;

    UserService userService;

    UserRepository userRepository;

    AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtService.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Set<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return JwtResponse.builder()
                .jwt(jwt)
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .roles(roles)
                .build();
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegistrationRequest request) throws UserExistedException, ObjectNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isPresent()) {
            throw new UserExistedException("Username is existed");
        }
        UserResponse userResponse = userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
