package com.springboot;

import com.springboot.util.EmailSenderUtil;
import jakarta.mail.Message;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

/**
 * Unit test for EmailSenderUtil: JavaMailSender is mocked, so no SMTP server / DB is needed.
 */
@ExtendWith(MockitoExtension.class)
class EmailSenderUtilTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailSenderUtil emailSenderUtil;

    @Test
    void sendTextEmail_buildsAndSendsMessage() {
        emailSenderUtil.sendTextEmail("anhtudo97@gmail.com", "Hello", "Test content");

        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(captor.capture());

        SimpleMailMessage sent = captor.getValue();
        assertArrayEquals(new String[]{"anhtudo97@gmail.com"}, sent.getTo());
        assertEquals("Hello", sent.getSubject());
        assertEquals("Test content", sent.getText());
        assertEquals("anhtudo97@gmail.com", sent.getFrom());
    }

    @Test
    void sendHtmlEmail_buildsMimeMessageAndSends() throws Exception {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailSenderUtil.sendHtmlEmail("receiver@gmail.com", "Hello", "<h1>Your OTP is 123123</h1>");

        verify(javaMailSender).send(mimeMessage);
        assertEquals("receiver@gmail.com", mimeMessage.getRecipients(Message.RecipientType.TO)[0].toString());
        assertEquals("Hello", mimeMessage.getSubject());
        assertEquals("anhtudo97@gmail.com", mimeMessage.getFrom()[0].toString());
    }

    @Test
    void sendHtmlEmail_bodyIsSentAsHtml() throws Exception {
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailSenderUtil.sendHtmlEmail("receiver@gmail.com", "Hello", "<h1>Your OTP is 123123</h1>");

        mimeMessage.saveChanges();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mimeMessage.writeTo(out);
        String raw = out.toString(StandardCharsets.UTF_8);

        assertTrue(raw.contains("<h1>Your OTP is 123123</h1>"));
        assertTrue(raw.contains("Content-Type: text/html"), "body must be text/html, but raw message was:\n" + raw);
    }

    @Test
    void sendHtmlEmail_otpTemplate_containsOtpAndNoLeftoverPlaceholder() throws Exception {
        String html = loadOtpTemplate("123123");
        MimeMessage mimeMessage = new MimeMessage((Session) null);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        emailSenderUtil.sendHtmlEmail("anhtudo0703@gmail.com", "Your OTP", html);

        mimeMessage.saveChanges();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        mimeMessage.writeTo(out);
        String raw = out.toString(StandardCharsets.UTF_8);

        verify(javaMailSender).send(mimeMessage);
        assertTrue(raw.contains("123123"), "OTP must appear in the mail body");
        assertFalse(raw.contains("{{otp}}"), "placeholder must be replaced");
    }

    /** Loads templates/email/otp-auth.html from the classpath and fills the {{otp}} placeholder. */
    private String loadOtpTemplate(String otp) throws Exception {
        try (InputStream in = getClass().getResourceAsStream("/templates/email/otp-auth.html")) {
            assertNotNull(in, "otp-auth.html template not found on classpath");
            return new String(in.readAllBytes(), StandardCharsets.UTF_8).replace("{{otp}}", otp);
        }
    }

    @Test
    void sendTextEmail_wrapsSenderFailureInRuntimeException() {
        doThrow(new MailSendException("SMTP down")).when(javaMailSender).send(any(SimpleMailMessage.class));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> emailSenderUtil.sendTextEmail("receiver@gmail.com", "Hello", "Test content"));

        assertEquals(MailSendException.class, ex.getCause().getClass());
    }
}
