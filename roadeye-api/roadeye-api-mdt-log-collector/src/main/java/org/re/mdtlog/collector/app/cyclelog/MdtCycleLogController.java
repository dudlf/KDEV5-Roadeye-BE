package org.re.mdtlog.collector.app.cyclelog;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.re.mdtlog.collector.app.common.dto.BaseMdtLogResponse;
import org.re.mdtlog.collector.app.cyclelog.dto.MdtAddCycleLogRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cycle-log")
public class MdtCycleLogController {
    private final MdtCycleLogService cycleLogService;

    @PostMapping
    public BaseMdtLogResponse addCycleLogs(@Valid @RequestBody MdtAddCycleLogRequest dto) {
        cycleLogService.addCycleLogs(dto);
        return new BaseMdtLogResponse(dto.carId());
    }
}
