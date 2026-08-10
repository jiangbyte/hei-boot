package github.jiangbyte.io.common.mail;

/**
 * 邮件发送门面：提供纯文本与 HTML 邮件发送能力。
 *
 * Author: Charlie
 */
public interface MailService {

    /** 发送纯文本邮件。 */
    void sendText(String to, String subject, String content);

    /** 发送 HTML 邮件。 */
    void sendHtml(String to, String subject, String htmlContent);
}
