package org.re.mdtlog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.mdtlog.dto.MdtCycleLogMessage;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.messaging.amqp.AMQPQueue;
import org.re.messaging.amqp.AMQPService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdtCycleLogService {
    private final AMQPService AMQPService;

    public void addCycleLogs(TransactionUUID tuid, MdtCycleLogMessage dto, MdtLogRequestTimeInfo timeInfo) {
        var queueName = AMQPQueue.QueueNames.MDT_CAR_LOCATION;
        var message = new MdtEventMessage<>(tuid.toString(), dto, timeInfo.sentAt(), timeInfo.receivedAt());
        AMQPService.sendWithQueueName(queueName, message);
    }
}
