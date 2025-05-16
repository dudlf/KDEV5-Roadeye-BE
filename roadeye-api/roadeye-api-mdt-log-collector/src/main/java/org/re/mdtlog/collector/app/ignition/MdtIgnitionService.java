package org.re.mdtlog.collector.app.ignition;

import lombok.RequiredArgsConstructor;
import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOnRequest;
import org.re.mdtlog.domain.MdtLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MdtIgnitionService {
    private final MdtLogRepository mdtLogRepository;

    public void ignitionOn(MdtIgnitionOnRequest dto) {
        var mdtLog = dto.toMdtLog();
        mdtLogRepository.save(mdtLog);
    }
}
