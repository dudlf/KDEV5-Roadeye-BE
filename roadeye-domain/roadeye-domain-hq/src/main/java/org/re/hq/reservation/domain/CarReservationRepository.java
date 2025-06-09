package org.re.hq.reservation.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM CarReservation r
        WHERE r.car.id = :carId
        AND r.reserveStatus IN :statuses
        AND r.reservationPeriod.rentStartAt <= :#{#reservationPeriod.rentEndAt}
        AND r.reservationPeriod.rentEndAt >= :#{#reservationPeriod.rentStartAt}
        """)
    boolean existsCarReservationsByReservationPeriodContaining(Long carId, List<ReserveStatus> statuses, ReservationPeriod reservationPeriod, Long companyId);

    @Query("""
        SELECT DISTINCT r.car.id FROM CarReservation r
        WHERE r.reserveStatus IN :statuses
        AND r.reservationPeriod.rentStartAt < :#{#reservationPeriod.rentEndAt}
        AND r.reservationPeriod.rentEndAt > :#{#reservationPeriod.rentStartAt}
        AND r.companyId = :companyId
        """)
    List<Long> findCarIdsWithReservationPeriod(
        ReservationPeriod reservationPeriod,
        List<ReserveStatus> statuses,
        Long companyId
    );

    @Query("""
        SELECT r FROM CarReservation r
        WHERE r.companyId = :companyId
        """)
    Page<CarReservation> findCarReservationsByCompanyId(Long companyId, Pageable pageable);

    Page<CarReservation> findCarReservationsByCarId(Long carId, Pageable pageable);

    @Query("""
        SELECT r.id FROM CarReservation  r
        WHERE r.car.id = :carId
        AND r.reserveStatus = 'APPROVED'
        AND :ignitionAt BETWEEN r.reservationPeriod.rentStartAt AND r.reservationPeriod.rentEndAt
        """)
    Optional<Long> findIdByCarIdAndIgnitionAt(Long carId, LocalDateTime ignitionAt);
}
