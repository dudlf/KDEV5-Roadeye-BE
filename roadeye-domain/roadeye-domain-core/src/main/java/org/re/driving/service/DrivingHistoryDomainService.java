package org.re.driving.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.common.stereotype.DomainService;
import org.re.driving.domain.DrivingHistory;
import org.re.driving.domain.DrivingHistoryStatus;
import org.re.driving.domain.DrivingSnapShot;
import org.re.driving.repository.DrivingHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    public Page<DrivingHistory> findAll(Pageable pageable) {
        return drivingHistoryRepository.findAll(pageable);
    }

    public List<DrivingHistory> getActiveDrivingHistory(){
        return drivingHistoryRepository.findByStatus(DrivingHistoryStatus.DRIVING);
    }

    public List<DrivingHistory> getDrivingHistoryByCarId(Long carId) {
        return drivingHistoryRepository.findByCarIdAndStatus(carId, DrivingHistoryStatus.DRIVING);
    }
}
