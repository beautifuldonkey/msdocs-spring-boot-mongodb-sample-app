package com.microsoft.azure.appservice.examples.springbootmongodb.service;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String fromEmail = "support@beautifuldonkeyproductions.com";

    public void sendEmail(String to, String subject, String body) {
        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            MimeMessage message = new MimeMessage(session);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(InternetAddress.parse(to));
//            message.setTo(InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            String aliasEmail = "support@beautifuldonkeyproductions.com";
            String aliasName = "Support";
            message.setFrom(new InternetAddress(aliasEmail, aliasName));
//        message.setFrom(fromEmail);
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }

    }
}
