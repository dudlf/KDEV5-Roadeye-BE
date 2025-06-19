package org.re.messaging.amqp;

import lombok.experimental.UtilityClass;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("amqp")
@Component
public class AMQPQueue {
    @Bean
    public Queue mdtCarLocationQueue() {
        return new Queue(QueueNames.MDT_CAR_LOCATION, true, false, false);
    }

    @Bean
    public Queue mdtIgnitionOnQueue() {
        return new Queue(QueueNames.MDT_IGNITION_ON, true, false, false);
    }

    @Bean
    public Queue mdtIgnitionOffQueue() {
        return new Queue(QueueNames.MDT_IGNITION_OFF, true, false, false);
    }

    @UtilityClass
    public static class QueueNames {
        public static final String MDT_CAR_LOCATION = "mdt.car.location";
        public static final String MDT_IGNITION_ON = "mdt.ignition.on";
        public static final String MDT_IGNITION_OFF = "mdt.ignition.off";
    }
}
