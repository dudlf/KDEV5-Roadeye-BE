package org.re.driving.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.car.domain.Car;
import org.re.common.stereotype.DomainService;
import org.re.driving.domain.DrivingHistory;
import org.re.driving.domain.DrivingHistoryStatus;
import org.re.driving.repository.DrivingHistoryRepository;
import org.re.mdtlog.domain.TransactionUUID;

import java.time.LocalDateTime;


@DomainService
@Transactional
@RequiredArgsConstructor
public class DrivingHistoryDomainService {
    private final DrivingHistoryRepository drivingHistoryRepository;

    public void createNew(Car car, LocalDateTime driveStartAt) {
        var drivingHistory = DrivingHistory.createNew(car, driveStartAt);
        drivingHistoryRepository.save(drivingHistory);
    }

    public DrivingHistory findHistoryInProgress(Car car, TransactionUUID transactionUUID) {
        return drivingHistoryRepository.findByCarAndTxUidAndStatus(car, transactionUUID, DrivingHistoryStatus.DRIVING);
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
