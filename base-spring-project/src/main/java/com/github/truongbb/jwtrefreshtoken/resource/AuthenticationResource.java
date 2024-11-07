package com.github.truongbb.jwtrefreshtoken.resource;

import com.github.truongbb.jwtrefreshtoken.exception.ExistedUserException;
import com.github.truongbb.jwtrefreshtoken.exception.InvalidRefreshTokenException;
import com.github.truongbb.jwtrefreshtoken.exception.ObjectNotFoundException;
import com.github.truongbb.jwtrefreshtoken.model.request.LoginRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.RefreshTokenRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.RegistrationRequest;
import com.github.truongbb.jwtrefreshtoken.model.response.JwtResponse;
import com.github.truongbb.jwtrefreshtoken.model.response.UserResponse;
import com.github.truongbb.jwtrefreshtoken.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/authentications")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationResource {

    AuthenticationService authenticateService;

    @PostMapping("/login")
    public JwtResponse authenticateUser(@Valid @RequestBody LoginRequest request) throws ObjectNotFoundException {
        return authenticateService.authenticate(request);
    }

    @PostMapping("/registration")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegistrationRequest request)
            throws ExistedUserException, ObjectNotFoundException, MessagingException {
        UserResponse userResponse = authenticateService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/refresh_token")
    public JwtResponse refreshToken(@RequestBody @Valid RefreshTokenRequest request)
            throws InvalidRefreshTokenException {
        return authenticateService.refreshToken(request);
    }

    @PostMapping("/logout")
    public void logout() {
        authenticateService.logout();
    }

}
