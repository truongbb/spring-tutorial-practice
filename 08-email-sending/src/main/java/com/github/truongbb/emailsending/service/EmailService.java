package com.github.truongbb.emailsending.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendSimpleMail(String receiver) {
        // Creating a simple mail message
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        // Setting up necessary details
        mailMessage.setFrom(sender);
        mailMessage.setTo(receiver);
        mailMessage.setText("Mã OTP của bạn là: 123456.\n\n Không chia sẻ mã này cho bất kỳ ai!");
        mailMessage.setSubject("[Techmaster] OTP Vefification");

        // Sending the mail
        javaMailSender.send(mailMessage);
    }

    public void sendMailWithAttachment(String receiver) throws MessagingException {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        // Setting multipart as true for attachments to
        // be send
        mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(sender);
        mimeMessageHelper.setTo(receiver);
        mimeMessageHelper.setText("Email có đính kèm file");
        mimeMessageHelper.setSubject("[DEMO MAIL] Gửi mail kèm file");

        // Adding the attachment
        FileSystemResource file = new FileSystemResource(new File("/Users/buibatruong/Desktop/1506ccd7-58d2-410d-b284-39c86a1dc2a4.png"));
        mimeMessageHelper.addAttachment(file.getFilename(), file);

        // Sending the mail
        javaMailSender.send(mimeMessage);
    }
}
