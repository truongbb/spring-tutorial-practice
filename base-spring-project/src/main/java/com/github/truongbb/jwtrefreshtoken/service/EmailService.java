package com.github.truongbb.jwtrefreshtoken.service;

import com.github.truongbb.jwtrefreshtoken.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailService {

    @Value("${spring.mail.username}")
    String fromEmail;

    @Value("${application.account.activation.activationUrl}")
    String activationLink;


    @Value("${application.account.passwordForgotten.url}")
    String passwordForgottenLink;

    final JavaMailSender javaMailSender;

    @Async
    public void sendActivationMail(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(user.getUsername());

        String url = activationLink.replace("{id}", user.getId().toString());
        String content = "<div>" +
                "<h1>Welcome to our website</h1>" +
                "<div>Please click <a href='" + url + "'>this link</a> to activate your account</div>" +
                "</div>";

        mimeMessageHelper.setText(content, true);
        mimeMessageHelper.setSubject("Registration Confirmation");

        javaMailSender.send(mimeMessage);
    }

    @Async
    public void sendForgotPasswordMail(User user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(fromEmail);
        mimeMessageHelper.setTo(user.getUsername());

        String url = passwordForgottenLink.replace("{id}", user.getId().toString());
        String content = "<div>" +
                "<h1>Forgot password</h1>" +
                "<div>Please click <a href='" + url + "'>this link</a> to change your password</div>" +
                "</div>";

        mimeMessageHelper.setText(content, true);
        mimeMessageHelper.setSubject("Password Forgotten");

        javaMailSender.send(mimeMessage);
    }
}
