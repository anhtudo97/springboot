package com.springboot.service;

import com.springboot.entity.mail.EmailEntity;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    String sendTextEmail(EmailEntity email);

    String sendHTMLEmail(EmailEntity email);

    String sendMailAttachmentEmail(EmailEntity email);
}
