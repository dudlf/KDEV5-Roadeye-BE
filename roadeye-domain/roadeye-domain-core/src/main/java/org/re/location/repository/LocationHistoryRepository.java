package org.re.location.repository;

import org.re.location.domain.LocationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationHistoryRepository extends JpaRepository<LocationHistory, Long> {
}
