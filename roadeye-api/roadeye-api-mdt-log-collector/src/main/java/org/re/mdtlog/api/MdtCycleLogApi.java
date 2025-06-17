package org.re.mdtlog.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.re.common.api.payload.BaseMdtLogMdtResponse;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdtlog.api.payload.MdtAddCycleLogRequest;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.mdtlog.service.MdtCycleLogService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cycle-log")
@RequiredArgsConstructor
public class MdtCycleLogApi {
    private final MdtCycleLogService cycleLogService;

    @PostMapping
    public BaseMdtLogMdtResponse addCycleLogs(
        @Valid @RequestBody MdtAddCycleLogRequest dto,
        @NotNull MdtLogRequestTimeInfo timeInfo,
        TransactionUUID tuid
    ) {
        cycleLogService.addCycleLogs(tuid, dto, timeInfo);
        return new BaseMdtLogMdtResponse(dto.carId());
    }
}
