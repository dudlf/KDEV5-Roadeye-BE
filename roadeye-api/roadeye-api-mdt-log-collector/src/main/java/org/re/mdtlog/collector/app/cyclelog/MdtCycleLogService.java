package org.re.mdtlog.collector.app.cyclelog;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.service.CarDomainService;
import org.re.mdtlog.collector.app.common.dto.MdtLogRequestTimeInfo;
import org.re.mdtlog.collector.app.cyclelog.dto.MdtAddCycleLogRequest;
import org.re.mdtlog.domain.MdtLogRepository;
import org.re.mdtlog.domain.TransactionUUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MdtCycleLogService {
    private final CarDomainService carDomainService;

    private final MdtLogRepository mdtLogRepository;

    public void addCycleLogs(TransactionUUID tuid, MdtAddCycleLogRequest dto, MdtLogRequestTimeInfo timeInfo) {
        var car = carDomainService.getCarById(dto.carId());
        car.updateLocation(dto.getLastLocation());
    }
}
