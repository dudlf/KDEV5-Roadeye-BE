package org.re.mdtlog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.mdtlog.dto.MdtIgnitionOffMessage;
import org.re.mdtlog.dto.MdtIgnitionOnMessage;
import org.re.messaging.amqp.AMQPQueue;
import org.re.messaging.amqp.AMQPService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdtIgnitionService {
    private final AMQPService AMQPService;

    public void ignitionOn(TransactionUUID tuid, MdtIgnitionOnMessage dto, MdtLogRequestTimeInfo timeInfo) {
        var queueName = AMQPQueue.QueueNames.MDT_IGNITION_ON;
        var message = new MdtEventMessage<>(tuid.toString(), dto, timeInfo.sentAt(), timeInfo.receivedAt());
        AMQPService.sendWithQueueName(queueName, message);
    }

    public void ignitionOff(TransactionUUID tuid, MdtIgnitionOffMessage dto, MdtLogRequestTimeInfo timeInfo) {
        var queueName = AMQPQueue.QueueNames.MDT_IGNITION_OFF;
        var message = new MdtEventMessage<>(tuid.toString(), dto, timeInfo.sentAt(), timeInfo.receivedAt());
        AMQPService.sendWithQueueName(queueName, message);
    }
}
