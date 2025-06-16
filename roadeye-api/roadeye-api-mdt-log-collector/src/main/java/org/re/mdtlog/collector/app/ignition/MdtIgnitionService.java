package org.re.mdtlog.collector.app.ignition;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.service.CarDomainService;
import org.re.mdtlog.collector.app.common.dto.MdtLogRequestTimeInfo;
import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOffRequest;
import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOnRequest;
import org.re.mdtlog.domain.MdtLogRepository;
import org.re.mdtlog.domain.MdtTransactionId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MdtIgnitionService {
    private final CarDomainService carDomainService;
    
    private final MdtLogRepository mdtLogRepository;

    public void ignitionOn(MdtTransactionId tuid, MdtIgnitionOnRequest dto, MdtLogRequestTimeInfo timeInfo) {
        var car = carDomainService.getCarById(dto.carId());
        carDomainService.turnOnIgnition(car, tuid);

        var mdtLog = dto.toMdtLog(tuid, timeInfo);
        mdtLogRepository.save(mdtLog);
    }

    public void ignitionOff(MdtTransactionId tuid, MdtIgnitionOffRequest dto, MdtLogRequestTimeInfo timeInfo) {
        var car = carDomainService.getCarById(dto.carId());
        carDomainService.turnOffIgnition(car, tuid);

        var mdtLog = dto.toMdtLog(tuid, timeInfo);
        mdtLogRepository.save(mdtLog);
    }
}
