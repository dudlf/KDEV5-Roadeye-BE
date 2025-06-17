package org.re.driving.repository;

import org.re.driving.domain.DrivingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingHistoryRepository extends JpaRepository<DrivingHistory, Long> {
}
