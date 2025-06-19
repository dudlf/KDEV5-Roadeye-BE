package org.re.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagingService {
    private final RabbitTemplate rabbitTemplate;
    private final RabbitProperties rabbitProperties;

    @RabbitListener
    public void send(Object message) {
        var exchange = rabbitProperties.getTemplate().getExchange();
        var routingKey = rabbitProperties.getTemplate().getRoutingKey();
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
