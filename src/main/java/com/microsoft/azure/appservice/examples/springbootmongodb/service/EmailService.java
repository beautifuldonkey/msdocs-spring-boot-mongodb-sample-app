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

    private String fromEmail = "do-not-reply@beautifuldonkeyproductions.com";

    public void sendEmail(String to, String subject, String body) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            String aliasEmail = "do-not-reply@beautifuldonkeyproductions.com";
            String aliasName = "Do not reply";
            message.setFrom(new InternetAddress(aliasEmail, aliasName));
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }

    }

    public void sendEmailAttachment(String to, String subject, String body, String attachmentData, String attachmentFilename) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            String aliasEmail = "do-not-reply@beautifuldonkeyproductions.com";
            String aliasName = "Do not reply";
            message.setFrom(new InternetAddress(aliasEmail, aliasName));

            messageHelper.addAttachment(attachmentFilename, new jakarta.mail.util.ByteArrayDataSource(attachmentData, "text/plain"));

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email with attachment", e);
        }
    }

}
