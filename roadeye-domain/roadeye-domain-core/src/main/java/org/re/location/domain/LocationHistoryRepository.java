package org.re.location.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationHistoryRepository extends JpaRepository<org.re.location.domain.LocationHistory, Long> {
}
