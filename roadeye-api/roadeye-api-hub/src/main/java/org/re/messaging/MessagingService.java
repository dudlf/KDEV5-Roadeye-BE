package org.re.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagingService {
    private final RabbitTemplate rabbitTemplate;

    public void sendWithQueueName(String queueName, Object message) {
        log.debug("Sending message to queue: {}", queueName);
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
