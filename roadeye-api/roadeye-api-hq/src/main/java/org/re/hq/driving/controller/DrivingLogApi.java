package org.re.hq.driving.controller;

import lombok.RequiredArgsConstructor;
import org.re.hq.common.dto.ListResponse;
import org.re.hq.driving.dto.DrivingLocationLogResponse;
import org.re.hq.driving.service.DrivingLogService;
import org.re.hq.tenant.TenantId;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/driving/drivingLogs")
@RequiredArgsConstructor
public class DrivingLogApi {
    private final DrivingLogService drivingLogService;

    @GetMapping("/reservation/{reservationId}")
    public ListResponse<DrivingLocationLogResponse> getDrivingLogsByReservationId(
        TenantId tenantId,
        @PathVariable Long reservationId
    ) {
        var logs = drivingLogService.findAllLocationLogsOfReservation(tenantId, reservationId);
        return ListResponse.of(logs, DrivingLocationLogResponse::from);
    }
}
