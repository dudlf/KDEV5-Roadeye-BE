package org.re.hq.driving.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.common.domain.DomainService;
import org.re.hq.driving.domain.DrivingHistory;
import org.re.hq.driving.domain.DrivingHistoryRepository;
import org.re.hq.driving.domain.DrivingSnapShot;

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
