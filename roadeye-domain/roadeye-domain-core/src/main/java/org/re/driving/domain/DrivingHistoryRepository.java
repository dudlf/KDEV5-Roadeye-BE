package org.re.driving.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DrivingHistoryRepository extends JpaRepository<org.re.driving.domain.DrivingHistory, Long> {
}
