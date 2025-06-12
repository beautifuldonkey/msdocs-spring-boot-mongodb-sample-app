package com.microsoft.azure.appservice.examples.springbootmongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    private String fromEmail = "support@beautifuldonkeyproductions.com";

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(fromEmail);
        mailSender.send(message);
    }

    public String sendSimpleMail()
    {

        // Try block to check for exceptions
        try {

            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();

            String emailTo = "jed.westover1986@gmail.com";
            String subject = "New Event Participant";
            String body = "A new participant has signed up to your event.";

            // Setting up necessary details
            mailMessage.setFrom(fromEmail);
            mailMessage.setTo(emailTo);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            // Sending the mail
            mailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return "Error while Sending Mail";
        }
    }
}
