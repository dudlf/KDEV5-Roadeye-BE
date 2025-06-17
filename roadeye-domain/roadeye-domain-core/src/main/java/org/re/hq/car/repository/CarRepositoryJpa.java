package org.re.hq.car.repository;

import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.hq.domain.common.EntityLifecycleStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CarRepositoryJpa extends JpaRepository<Car, Long> {
    Page<Car> findByCompanyIdAndStatus(Long companyId, EntityLifecycleStatus entityLifecycleStatus, Pageable pageable);

    List<Car> findAllByCompanyIdAndStatus(Long companyId, EntityLifecycleStatus entityLifecycleStatus);

    @Query("""
        SELECT c
        FROM Car c
        WHERE c.company.id = :companyId
          AND c.mdtStatus.ignition = :ignitionStatus
          AND c.status = :entityLifecycleStatus
        """)
    Page<Car> findByCompanyIdAndIgnitionStatusAndStatus(Long companyId, CarIgnitionStatus ignitionStatus, EntityLifecycleStatus entityLifecycleStatus, Pageable pageable);

    Long countByCompanyIdAndStatus(Long companyId, EntityLifecycleStatus status);

    @Query("""
        SELECT COUNT(c)
        FROM Car c
        WHERE c.company.id = :companyId
          AND c.mdtStatus.ignition = :ignitionStatus
          AND c.status = :entityLifecycleStatus
        """)
    Long countByCompanyIdAndIgnitionStatusAndStatus(Long companyId, CarIgnitionStatus ignitionStatus, EntityLifecycleStatus entityLifecycleStatus);

    Optional<Car> findByCompanyIdAndIdAndStatus(Long companyId, Long id, EntityLifecycleStatus status);
}
