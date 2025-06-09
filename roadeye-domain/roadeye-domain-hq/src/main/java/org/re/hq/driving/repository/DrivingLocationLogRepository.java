package org.re.hq.driving.repository;

import org.re.hq.driving.domain.DrivingLocationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DrivingLocationLogRepository extends JpaRepository<DrivingLocationLog, Long> {
    @Query("""
        SELECT h
          FROM DrivingLocationLog h
         WHERE h.id.carReservation.id = :id
           AND h.id.sequence = (
               SELECT MAX(clh.id.sequence)
                 FROM DrivingLocationLog clh
                WHERE clh.id.carReservation.id = :id
           )
        """)
    Optional<DrivingLocationLog> findLastLogByReservationId(Long id);
}
