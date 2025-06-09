package org.re.hq.driving.service;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.re.hq.domain.common.DomainService;
import org.re.hq.driving.domain.DrivingLocationLog;
import org.re.hq.driving.dto.DrivingLocationLogCreationCommand;
import org.re.hq.driving.repository.DrivingLocationLogRepository;
import org.re.hq.reservation.domain.CarReservation;
import org.springframework.transaction.annotation.Transactional;

@DomainService
@Transactional
@RequiredArgsConstructor
public class DrivingLocationLogDomainService {
    private final DrivingLocationLogRepository drivingLocationLogRepository;

    @Nullable
    public DrivingLocationLog getLastHistory(CarReservation carReservation) {
        return drivingLocationLogRepository.findLastLogByReservationId(carReservation.getId())
            .orElse(null);
    }

    public DrivingLocationLog createHistory(DrivingLocationLogCreationCommand command) {
        var history = DrivingLocationLog.create(command);
        return drivingLocationLogRepository.save(history);
    }
}
