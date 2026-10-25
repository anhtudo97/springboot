package com.springboot;

import com.springboot.util.EmailSenderUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.mail.autoconfigure.MailSenderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test gui email THAT qua Gmail SMTP (moi lan chay se gui 1 mail that).
 *
 * - Cau hinh SMTP (host, username, password...) lay tu spring.mail.* trong application.yaml.
 * - Gmail yeu cau App Password: https://myaccount.google.com/apppasswords
 * - Chi nap EmailSenderUtil + cau hinh mail, KHONG nap JPA nen khong can bat MySQL.
 */
@SpringBootTest(classes = {EmailSenderUtil.class, MailSenderAutoConfiguration.class})
class EmailSenderUtilRealSendTest {

    // Nguoi nhan mail test (doi thanh email cua ban neu can)
    private static final String RECEIVER = "anhtudo0703@gmail.com";

    // Duong dan template trong src/main/resources (classpath bat dau tu thu muc resources)
    private static final String OTP_TEMPLATE_PATH = "/templates/email/otp-auth.html";

    // Spring tu tao va inject bean EmailSenderUtil (class nay co @Component)
    @Autowired
    private EmailSenderUtil emailSenderUtil;

    /**
     * Gui mail HTML: doc file otp-auth.html -> thay {{otp}} bang ma OTP -> gui di.
     */
    @Test
    void sendRealHtmlEmail() throws Exception {
        // Buoc 1: doc noi dung file template tu classpath
        String html;
        try (InputStream in = getClass().getResourceAsStream(OTP_TEMPLATE_PATH)) {
            // Neu khong tim thay file thi bao loi ngay, tranh NullPointerException kho hieu
            assertNotNull(in, "Khong tim thay template: " + OTP_TEMPLATE_PATH);
            html = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }

        // Buoc 2: thay placeholder {{otp}} trong template bang ma OTP that
        String otp = "00000";
        html = html.replace("{{otp}}", otp);

        // Buoc 3: gui mail (sendHtmlEmail gui noi dung dang text/html)
        emailSenderUtil.sendHtmlEmail(RECEIVER, "Your OTP code", html);
    }

    /**
     * Gui mail chu thuong (plain text), khong dung template.
     */
    @Test
    void sendRealEmail() {
        emailSenderUtil.sendTextEmail(RECEIVER, "Spring Boot test mail", "Your OTP is 123123");
    }
}
