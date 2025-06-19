package org.re.mdtlog.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.messaging.amqp.AMQPQueue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoadeyeMdtLogConsumer {
    @RabbitListener(queues = AMQPQueue.QueueNames.MDT_IGNITION_ON)
    public void handleMdtIgnitionOnMessage(@Payload Object message) {
        log.info("Received MDT ignition message: {}", message);
    }

    @RabbitListener(queues = AMQPQueue.QueueNames.MDT_IGNITION_OFF)
    public void handleMdtIgnitionOffMessage(@Payload Object message) {
        log.info("Received MDT ignition off message: {}", message);
    }

    @RabbitListener(queues = AMQPQueue.QueueNames.MDT_CAR_LOCATION)
    public void handleMdtCarLocationMessage(@Payload Object message) {
        log.info("Received MDT car location message: {}", message);
    }
}
