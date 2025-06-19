package org.re.mdtlog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.mdtlog.dto.MdtIgnitionOffMessage;
import org.re.mdtlog.dto.MdtIgnitionOnMessage;
import org.re.messaging.MessagingService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdtIgnitionService {
    private final MessagingService messagingService;

    public void ignitionOn(TransactionUUID tuid, MdtIgnitionOnMessage dto, MdtLogRequestTimeInfo timeInfo) {
        var message = new MdtEventMessage<>(tuid.toString(), dto, timeInfo.sentAt(), timeInfo.receivedAt());
        messagingService.send(message);
    }

    public void ignitionOff(TransactionUUID tuid, MdtIgnitionOffMessage dto, MdtLogRequestTimeInfo timeInfo) {
        var message = new MdtEventMessage<>(tuid.toString(), dto, timeInfo.sentAt(), timeInfo.receivedAt());
        messagingService.send(message);
    }
}
