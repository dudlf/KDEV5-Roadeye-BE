package org.re.mdtlog.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.mdtlog.dto.MdtCycleLogMessage;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.messaging.MessagingService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdtCycleLogService {
    private final MessagingService messagingService;

    public void addCycleLogs(TransactionUUID tuid, MdtCycleLogMessage dto, MdtLogRequestTimeInfo timeInfo) {
        var message = new MdtEventMessage<>(tuid.toString(), dto, timeInfo.sentAt(), timeInfo.receivedAt());
        messagingService.send(message);
    }
}
