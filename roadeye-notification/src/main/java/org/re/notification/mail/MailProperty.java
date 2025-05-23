package org.re.notification.mail;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

@ConfigurationPropertiesBinding
@ConfigurationProperties("spring.mail")
public record MailProperty(
    String host,
    String username,
    String password,
    int port
) {
}
