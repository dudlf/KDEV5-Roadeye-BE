package org.re.driving.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.common.domain.DomainService;
import org.re.driving.domain.DrivingHistory;
import org.re.driving.domain.DrivingSnapShot;
import org.re.driving.repository.DrivingHistoryRepository;

@DomainService
@Transactional
@RequiredArgsConstructor
public class DrivingHistoryDomainService {
    private final DrivingHistoryRepository drivingHistoryRepository;

    public void save(DrivingHistory drivingHistory) {
        drivingHistoryRepository.save(drivingHistory);
    }

    public void update(DrivingHistory drivingHistory, DrivingSnapShot drivingSnapShot) {
        drivingHistory.end(drivingSnapShot);
    }

    public DrivingHistory findById(Long id) {
        return drivingHistoryRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("DrivingHistory not found"));
    }
}
