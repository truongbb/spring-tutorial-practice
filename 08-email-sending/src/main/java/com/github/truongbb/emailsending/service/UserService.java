package com.github.truongbb.emailsending.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {

    EmailService emailService;

    public void sendOtp(String email) {
        emailService.sendSimpleMail(email);
    }

    public void sendAttachedMail(String email) throws MessagingException {
        emailService.sendMailWithAttachment(email);
    }
}
