package org.re.hq.driving.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.driving.domain.DrivingLocationLog;
import org.re.hq.reservation.service.CarReservationDomainService;
import org.re.hq.tenant.TenantId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class DrivingLogService {
    private final CarReservationDomainService carReservationDomainService;
    private final DrivingLocationLogDomainService drivingLocationLogDomainService;

    public List<DrivingLocationLog> findAllLocationLogsOfReservation(TenantId tenantId, Long reservationId) {
        var reservation = carReservationDomainService.findByIdAndCompanyId(reservationId, tenantId.value());
        return drivingLocationLogDomainService.findAllLogsOfReservation(reservation);
    }
}
