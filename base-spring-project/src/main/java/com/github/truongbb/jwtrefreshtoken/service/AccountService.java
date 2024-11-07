package com.github.truongbb.jwtrefreshtoken.service;

import com.github.truongbb.jwtrefreshtoken.entity.User;
import com.github.truongbb.jwtrefreshtoken.exception.ExpiredEmailActivationUrlException;
import com.github.truongbb.jwtrefreshtoken.exception.ExpiredPasswordForgottenUrlException;
import com.github.truongbb.jwtrefreshtoken.exception.ObjectNotFoundException;
import com.github.truongbb.jwtrefreshtoken.exception.PasswordNotMatchedException;
import com.github.truongbb.jwtrefreshtoken.model.request.ForgotPasswordEmailRequest;
import com.github.truongbb.jwtrefreshtoken.model.request.PasswordChangingRequest;
import com.github.truongbb.jwtrefreshtoken.repository.UserRepository;
import com.github.truongbb.jwtrefreshtoken.statics.UserStatus;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountService {

    final UserRepository userRepository;

    final PasswordEncoder passwordEncoder;

    final EmailService emailService;

    @Value("${application.account.activation.expiredDurationInMilliseconds}")
    long activationMailExpiredDurationInMilliseconds;

    @Value("${application.account.passwordForgotten.expiredDurationInMilliseconds}")
    long passwordForgottenExpiredDurationInMilliseconds;

    @Value("${application.account.activation.maxResendTimes}")
    int activationMailMaxSentCount;

    public void changePassword(Long id, PasswordChangingRequest request) throws ObjectNotFoundException, PasswordNotMatchedException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        if (!request.getPassword().equals(request.getConfirmedPassword())) {
            throw new PasswordNotMatchedException("Password not matched");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    public void activateAccount(Long userId) throws ObjectNotFoundException, ExpiredEmailActivationUrlException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        // check xem link active het han chua
        LocalDateTime activationMailSentAt = user.getActivationMailSentAt();
        if (activationMailSentAt.plusSeconds(activationMailExpiredDurationInMilliseconds / 1000).isBefore(LocalDateTime.now())) {
            throw new ExpiredEmailActivationUrlException("Activation link expired");
        }
        user.setStatus(UserStatus.ACTIVATED);
        userRepository.save(user);
    }

    public void sendActivationEmail(Long id) throws MessagingException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        if (user.getActivationMailSentCount() > activationMailMaxSentCount) {
            throw new MessagingException("Activation email has been sent over " + activationMailMaxSentCount + " times");
        }
        emailService.sendActivationMail(user);
        user.setActivationMailSentCount(user.getActivationMailSentCount() + 1);
        userRepository.save(user);
    }

    public void sendForgotPasswordEmail(@Valid ForgotPasswordEmailRequest request) throws MessagingException {
        // TODO - cần check so lần gửi tối đa trong 1 khoag thơi gian (vi du chi duoc gui toi da 3 mail trong vong 1h)

        // cần check xem email có tồn tại trong hệ thống không
        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email not found"));

        // gửi mail
        emailService.sendForgotPasswordMail(user);
        user.setForgotPasswordMailSentAt(LocalDateTime.now());
        userRepository.save(user);
    }

    public void changeForgotPassword(Long userId, PasswordChangingRequest request)
            throws ObjectNotFoundException, ExpiredPasswordForgottenUrlException, PasswordNotMatchedException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("User not found"));

        // check xem link active het han chua
        LocalDateTime forgotPasswordMailSentAt = user.getForgotPasswordMailSentAt();
        if (forgotPasswordMailSentAt.plusSeconds(passwordForgottenExpiredDurationInMilliseconds / 1000).isBefore(LocalDateTime.now())) {
            throw new ExpiredPasswordForgottenUrlException("Forgot password link expired");
        }

        if (!request.getPassword().equals(request.getConfirmedPassword())) {
            throw new PasswordNotMatchedException("Password not matched");
        }

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
