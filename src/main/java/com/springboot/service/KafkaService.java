package com.springboot.service;

import com.springboot.entity.mail.EmailEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class KafkaService {

    public static final String OTP_TOPIC = "otp-auth-topic";

    @Autowired
    private EmailService emailService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = OTP_TOPIC, groupId = "otp-group-id")
    public void listenOTP(String message) {
        try {
            JsonNode jsonNode = objectMapper.readTree(message);

            /*
             * jsonNode
             * email
             * otp
             * */
            String email = jsonNode.get("email").asString();
            String otp = jsonNode.get("otp").asString();
            log.info("otp {} email {}", otp, email);

            EmailEntity emailEntity = new EmailEntity();
            emailEntity.setToEmail(email);
            emailEntity.setSubject("otp auth");
            emailEntity.setMessageBody("OTP is " + otp);

            String result = emailService.sendTextEmail(emailEntity);

            log.info("result {}", result);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}

