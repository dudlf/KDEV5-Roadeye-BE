package org.re.notification.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfiguration {

    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_DEBUG = "mail.smtp.debug";
    private static final String MAIL_CONNECTION_TIMEOUT = "mail.smtp.connectiontimeout";
    private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    @Bean
    public JavaMailSender mailSender(MailProperty mailProperty) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailProperty.host());
        javaMailSender.setUsername(mailProperty.username());
        javaMailSender.setPassword(mailProperty.password());
        javaMailSender.setPort(mailProperty.port());

        Properties properties = javaMailSender.getJavaMailProperties();
        properties.put(MAIL_SMTP_AUTH, true);
        properties.put(MAIL_DEBUG, true);
        properties.put(MAIL_CONNECTION_TIMEOUT, 1000);
        properties.put(MAIL_SMTP_STARTTLS_ENABLE, true);
        javaMailSender.setJavaMailProperties(properties);
        javaMailSender.setDefaultEncoding("UTF-8");

        return javaMailSender;
    }

}
