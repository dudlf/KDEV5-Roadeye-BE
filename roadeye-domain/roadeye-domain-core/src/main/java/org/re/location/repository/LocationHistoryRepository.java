package org.re.location.repository;

import org.re.location.domain.LocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
    List<LocationHistory> findByDrivingId(Long drivingId);

    @Query("""
        SELECT lh
        FROM LocationHistory lh
        WHERE lh.id IN (
            SELECT MAX(lh2.id)
            FROM LocationHistory lh2
            WHERE lh2.drivingId IN :drivingIds
            GROUP BY lh2.drivingId
        )
    """)
    List<LocationHistory> findLatestLocationHistory(List<Long> drivingHistoryIds);

}
