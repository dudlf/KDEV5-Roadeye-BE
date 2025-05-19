package org.re.mdtlog.collector.app.ignition;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.re.mdtlog.collector.app.common.dto.BaseMdtLogResponse;
import org.re.mdtlog.collector.app.common.dto.MdtLogRequestTimeInfo;
import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOffRequest;
import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOnRequest;
import org.re.mdtlog.domain.MdtTransactionId;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ignition")
public class MdtIgnitionController {
    private final MdtIgnitionService mdtIgnitionService;

    @PostMapping(
        value = "/on",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseMdtLogResponse ignitionOn(
        @Valid @RequestBody MdtIgnitionOnRequest dto,
        @NotNull MdtLogRequestTimeInfo timeInfo,
        MdtTransactionId tuid
    ) {
        mdtIgnitionService.ignitionOn(tuid, dto, timeInfo);
        return new BaseMdtLogResponse(dto.carId());
    }

    @PostMapping(
        value = "/off",
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseMdtLogResponse ignitionOn(
        @Valid @RequestBody MdtIgnitionOffRequest dto,
        @NotNull MdtLogRequestTimeInfo timeInfo,
        MdtTransactionId tuid
    ) {
        mdtIgnitionService.ignitionOff(tuid, dto, timeInfo);
        return new BaseMdtLogResponse(dto.carId());
    }
}
