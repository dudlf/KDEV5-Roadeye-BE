package org.re.mdtlog.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.re.car.service.CarDomainService;
import org.re.mdtlog.dto.MdtCycleLogMessage;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.mdtlog.dto.MdtIgnitionOffMessage;
import org.re.mdtlog.dto.MdtIgnitionOnMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MdtEventHandler {
    private final MdtLogService mdtLogService;
    private final CarDomainService carDomainService;

    @Transactional
    public void handleMdtCarLocationMessage(MdtEventMessage<MdtCycleLogMessage> message) {
        var logs = message.payload().toLogEntries(message.transactionId(), message.sentAt(), message.receivedAt());
        mdtLogService.save(logs);

        var car = carDomainService.getCarById(message.payload().carId());
        car.updateMdtStatus(message);
    }

    @Transactional
    public void handleMdtIgnitionOnMessage(MdtEventMessage<MdtIgnitionOnMessage> message) {
        var log = message.payload().toLogEntry(message.transactionId(), message.sentAt(), message.receivedAt());
        mdtLogService.save(log);

        var car = carDomainService.getCarById(message.payload().carId());
        car.turnOnIgnition(message);
    }

    @Transactional
    public void handleMdtIgnitionOffMessage(MdtEventMessage<MdtIgnitionOffMessage> message) {
        var log = message.payload().toLogEntry(message.transactionId(), message.sentAt(), message.receivedAt());
        mdtLogService.save(log);

        var car = carDomainService.getCarById(message.payload().carId());
        car.turnOffIgnition(message);
    }
}
