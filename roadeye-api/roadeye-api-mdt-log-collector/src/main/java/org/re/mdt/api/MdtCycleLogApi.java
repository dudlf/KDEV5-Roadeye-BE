package org.re.mdt.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.re.common.api.payload.BaseMdtLogMdtResponse;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdt.api.payload.MdtAddCycleLogRequest;
import org.re.mdt.service.MdtCycleLogService;
import org.re.mdtlog.domain.TransactionUUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cycle-log")
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
