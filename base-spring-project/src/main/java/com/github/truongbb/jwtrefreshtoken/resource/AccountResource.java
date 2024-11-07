package com.github.truongbb.jwtrefreshtoken.resource;

import com.github.truongbb.jwtrefreshtoken.exception.ExpiredEmailActivationUrlException;
import com.github.truongbb.jwtrefreshtoken.exception.ExpiredPasswordForgottenUrlException;
import com.github.truongbb.jwtrefreshtoken.exception.ObjectNotFoundException;
import com.github.truongbb.jwtrefreshtoken.exception.PasswordNotMatchedException;
import com.github.truongbb.jwtrefreshtoken.model.request.ForgotPasswordEmailRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.PasswordChangingRequest;
import com.github.truongbb.jwtrefreshtoken.service.AccountService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AccountResource {

    AccountService accountService;

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody @Valid PasswordChangingRequest request)
            throws ObjectNotFoundException, PasswordNotMatchedException {
        accountService.changePassword(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/activations")
    public ResponseEntity<?> activateAccount(@PathVariable Long id)
            throws ObjectNotFoundException, ExpiredEmailActivationUrlException {
        accountService.activateAccount(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/activation_emails")
    public ResponseEntity<?> sendActivationEmail(@PathVariable Long id)
            throws MessagingException {
        accountService.sendActivationEmail(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/password_forgotten_emails")
    public ResponseEntity<?> sendForgotPasswordEmail(@RequestBody @Valid ForgotPasswordEmailRequest request)
            throws MessagingException {
        accountService.sendForgotPasswordEmail(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/password_forgotten")
    public ResponseEntity<?> changeForgotPassword(@PathVariable Long id, @RequestBody @Valid PasswordChangingRequest request)
            throws ObjectNotFoundException, ExpiredPasswordForgottenUrlException, PasswordNotMatchedException {
        accountService.changeForgotPassword(id, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
