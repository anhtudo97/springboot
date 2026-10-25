package com.springboot.controller.email;

import com.springboot.entity.mail.EmailEntity;
import com.springboot.service.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send_text")
    public String sendTextEmail(@Valid @RequestBody EmailEntity emailEntity) {
        return emailService.sendTextEmail(emailEntity);
    }

    @PostMapping("/send_html")
    public String sendHTMLEmail(@Valid @RequestBody EmailEntity emailEntity) {
        return emailService.sendHTMLEmail(emailEntity);
    }
}
