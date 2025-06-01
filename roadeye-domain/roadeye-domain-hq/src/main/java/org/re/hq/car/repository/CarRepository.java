package org.re.hq.car.repository;

import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.hq.domain.common.EntityLifecycleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByCompanyIdAndStatus(Long companyId, EntityLifecycleStatus entityLifecycleStatus, Pageable pageable);

    @Query("""
        SELECT c
        FROM Car c
        WHERE c.company.id = :companyId
          AND c.mdtStatus.ignition = :ignitionStatus
          AND c.status = :entityLifecycleStatus
        """)
    Page<Car> findByCompanyIdAndIgnitionStatusAndStatus(Long companyId, CarIgnitionStatus ignitionStatus, EntityLifecycleStatus entityLifecycleStatus, Pageable pageable);

    Long countByCompanyIdAndStatus(Long companyId, EntityLifecycleStatus status);

    Optional<Car> findByCompanyIdAndIdAndStatus(Long companyId, Long id, EntityLifecycleStatus status);
}
