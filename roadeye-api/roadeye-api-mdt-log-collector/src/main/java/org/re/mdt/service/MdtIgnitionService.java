package org.re.mdt.service;

import lombok.RequiredArgsConstructor;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdt.api.payload.MdtIgnitionOffRequest;
import org.re.mdt.api.payload.MdtIgnitionOnRequest;
import org.re.mdtlog.domain.MdtLogRepository;
import org.re.mdtlog.domain.TransactionUUID;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MdtIgnitionService {
    private final MdtLogRepository mdtLogRepository;

    public void ignitionOn(TransactionUUID tuid, MdtIgnitionOnRequest dto, MdtLogRequestTimeInfo timeInfo) {
        var mdtLog = dto.toMdtLog(tuid, timeInfo);
        mdtLogRepository.save(mdtLog);
    }

    public void ignitionOff(TransactionUUID tuid, MdtIgnitionOffRequest dto, MdtLogRequestTimeInfo timeInfo) {
        var mdtLog = dto.toMdtLog(tuid, timeInfo);
        mdtLogRepository.save(mdtLog);
    }
}
