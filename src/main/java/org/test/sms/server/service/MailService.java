package org.test.sms.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MailService {

    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPasswordMail(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setSubject("SMS app password");
        message.setTo(to);
        message.setText("Your password is: " + password);

        mailSender.send(message);
    }
}