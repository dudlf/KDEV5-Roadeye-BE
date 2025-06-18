package org.re.driving.repository;

import org.re.driving.domain.DrivingHistory;
import org.re.driving.domain.DrivingHistoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DrivingHistoryRepository extends JpaRepository<DrivingHistory, Long> {
    List<DrivingHistory> findByStatus(DrivingHistoryStatus status);
}
