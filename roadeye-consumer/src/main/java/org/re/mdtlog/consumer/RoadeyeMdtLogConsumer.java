package org.re.mdtlog.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RoadeyeMdtLogConsumer {
    @RabbitListener(queues = "mdt-ignition-queue")
    public void handleMdtIgnitionMessage(String message) {
        log.info("Received MDT ignition message: {}", message);
    }

    @RabbitListener(queues = "mdt-cycle-queue")
    public void handleMdtCycleMessage(String message) {
        log.info("Received MDT cycle message: {}", message);
    }
}
