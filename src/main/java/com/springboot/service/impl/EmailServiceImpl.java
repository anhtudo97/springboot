package com.springboot.service.impl;

import com.springboot.entity.mail.EmailEntity;
import com.springboot.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_HOST = "anhtudo0703@gmail.com";

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendTextEmail(EmailEntity email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(email.getToEmail());
        simpleMailMessage.setSubject(email.getSubject());
        simpleMailMessage.setText(email.getMessageBody());
        simpleMailMessage.setFrom(EMAIL_HOST);

        try {
            javaMailSender.send(simpleMailMessage);
            return ("Email sent successfully");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendHTMLEmail(EmailEntity email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(EMAIL_HOST);
            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            helper.setText(email.getMessageBody(), true);

            javaMailSender.send(message);
            return ("Email sent successfully");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String sendMailAttachmentEmail(EmailEntity email) {
        return "";
    }
}
