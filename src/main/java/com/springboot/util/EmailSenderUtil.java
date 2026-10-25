package com.springboot.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailSenderUtil {

    private static final String EMAIL_HOST = "anhtudo97@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendTextEmail(String to, String subject, String content) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom(EMAIL_HOST);

        try {
            javaMailSender.send(simpleMailMessage);
            System.out.println("Email sent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public  void sendHtmlEmail(String to , String subject, String content) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(EMAIL_HOST);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
