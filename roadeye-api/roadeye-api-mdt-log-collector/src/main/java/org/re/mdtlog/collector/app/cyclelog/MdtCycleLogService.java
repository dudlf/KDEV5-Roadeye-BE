package org.re.mdtlog.collector.app.cyclelog;

import lombok.RequiredArgsConstructor;
import org.re.mdtlog.collector.app.cyclelog.dto.MdtAddCycleLogRequest;
import org.re.mdtlog.domain.MdtLogRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MdtCycleLogService {
    private final MdtLogRepository mdtLogRepository;

    public void addCycleLogs(MdtAddCycleLogRequest dto) {
        var mdtLogs = dto.toMdtLogList();
        mdtLogRepository.saveAll(mdtLogs);
    }
}
