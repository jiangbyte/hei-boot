package github.jiangbyte.io.common.mail;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * 基于 JavaMailSender 的默认邮件发送实现。
 *
 * Author: Charlie
 */
@RequiredArgsConstructor
public class DefaultMailService implements MailService {

    private static final Logger log = LoggerFactory.getLogger(DefaultMailService.class);

    private final JavaMailSender mailSender;

    /** 发送纯文本邮件。 */
    @Override
    @CircuitBreaker(name = "mail", fallbackMethod = "sendTextFallback")
    @Bulkhead(name = "mail")
    public void sendText(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }

    /** 发送 HTML 邮件。 */
    @Override
    @CircuitBreaker(name = "mail", fallbackMethod = "sendHtmlFallback")
    @Bulkhead(name = "mail")
    public void sendHtml(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            throw new IllegalStateException("Failed to send html mail", exception);
        }
    }

    @SuppressWarnings("unused")
    private void sendTextFallback(String to, String subject, String content, Throwable ex) {
        log.warn("Mail circuit open/fallback for text to={}: {}", to, ex.toString());
        throw new IllegalStateException("Mail service unavailable", ex);
    }

    @SuppressWarnings("unused")
    private void sendHtmlFallback(String to, String subject, String htmlContent, Throwable ex) {
        log.warn("Mail circuit open/fallback for html to={}: {}", to, ex.toString());
        throw new IllegalStateException("Mail service unavailable", ex);
    }
}
