package org.test.sms.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.test.sms.common.entity.general.User;

@Service
@Transactional
@Async
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordMail(User user, String password) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("SMS app credentials");
        message.setTo(user.getEmail());

        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("Hello, ");
        textBuilder.append(user.getName());
        textBuilder.append("\n");
        textBuilder.append("\n");
        textBuilder.append("These are your SMS app login credentials:");
        textBuilder.append("\n");
        textBuilder.append("\n");
        textBuilder.append("username: ");
        textBuilder.append(user.getUsername());
        textBuilder.append("\n");
        textBuilder.append("password: ");
        textBuilder.append(password);

        message.setText(textBuilder.toString());

        mailSender.send(message);
    }

    public void sendPasswordResetMail(long userId) {

    }
}