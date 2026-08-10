package github.jiangbyte.io.common.mail.config;

import github.jiangbyte.io.common.mail.DefaultMailService;
import github.jiangbyte.io.common.mail.MailService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 邮件模块自动配置：存在 JavaMailSender 时注册默认 MailService。
 *
 * Author: Charlie
 */
@AutoConfiguration
@ConditionalOnClass(JavaMailSender.class)
public class MailAutoConfiguration {

    /** 注册默认 MailService。 */
    @Bean
    @ConditionalOnBean(JavaMailSender.class)
    @ConditionalOnMissingBean
    public MailService mailService(JavaMailSender mailSender) {
        return new DefaultMailService(mailSender);
    }
}
