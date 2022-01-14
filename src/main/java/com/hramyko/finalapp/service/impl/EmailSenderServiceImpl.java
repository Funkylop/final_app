package com.hramyko.finalapp.service.impl;

import com.hramyko.finalapp.persistence.entity.ConfirmationToken;
import com.hramyko.finalapp.persistence.entity.TokenType;
import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.service.ConfirmationTokenService;
import com.hramyko.finalapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmailSenderServiceImpl {

    private final JavaMailSender javaMailSender;
    private final ConfirmationTokenService confirmationTokenService;
    private final UserService userService;

    @Autowired
    public EmailSenderServiceImpl(JavaMailSender javaMailSender, UserService userService,
                                  ConfirmationTokenService confirmationTokenService) {
        this.javaMailSender = javaMailSender;
        this.confirmationTokenService = confirmationTokenService;
        this.userService = userService;
    }

    @Transactional
    public void registrationConfirmationMessage(User user) {
        String confirmationUrl = "http://localhost:8080/registration/confirm/";
        String message = "Hello! To register on our website, please, redirect to the following url: ";
        SimpleMailMessage mail = generateMail(user, "Registration Confirmation",
                confirmationUrl, message, "REGISTRATION_TOKEN");
        javaMailSender.send(mail);
    }

    @Transactional
    public void resetPasswordMessage(User user) {
        String confirmationUrl = "http://localhost:8080/auth/reset/";
        String message = "Hello! To reset your password, please, redirect to the following url: ";
        SimpleMailMessage mail = generateMail(user, "Reset Password",
                confirmationUrl, message, "RESET_PASSWORD_TOKEN");
        javaMailSender.send(mail);
    }

    @Transactional
    public void notifyMessage(User user) {
        String message = "Hello! You has been updated to trader!";
        SimpleMailMessage mail = generateMail(user, "Account role",
                "", message, null);
        javaMailSender.send(mail);
    }

    private SimpleMailMessage generateMail(User user, String subject,
                                           String confirmationUrl, String message, String tokenType) {

        if (tokenType != null) {
            ConfirmationToken confirmationToken = new ConfirmationToken(user.getId(), TokenType.valueOf(tokenType));
            confirmationTokenService.createConfirmationToken(confirmationToken);
            confirmationUrl = confirmationUrl + confirmationToken.getConfirmationToken();
        } else {
            confirmationUrl = "";
        }
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject(subject);
        email.setText(message + confirmationUrl);

        return email;
    }
}