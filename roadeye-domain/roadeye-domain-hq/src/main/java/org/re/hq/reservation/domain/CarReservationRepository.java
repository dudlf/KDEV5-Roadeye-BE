package org.re.hq.reservation.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CarReservationRepository extends JpaRepository<CarReservation, Long> {
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM CarReservation r
        WHERE r.carId = :carId
        AND r.reserveStatus IN :statuses
        AND r.reservationPeriod.rentStartAt <= :#{#reservationPeriod.rentEndAt}
        AND r.reservationPeriod.rentEndAt >= :#{#reservationPeriod.rentStartAt}
        """)
    boolean existsCarReservationsByReservationPeriodContaining(Long carId, List<ReserveStatus> statuses, ReservationPeriod reservationPeriod, Long companyId);

    @Query("""
        SELECT DISTINCT r.carId FROM CarReservation r
        JOIN Car c ON r.carId = c.id
        WHERE r.reserveStatus IN :statuses
        AND r.reservationPeriod.rentStartAt < :#{#reservationPeriod.rentEndAt}
        AND r.reservationPeriod.rentEndAt > :#{#reservationPeriod.rentStartAt}
        AND c.company.id = :companyId
        """)
    List<Long> findCarIdsWithReservationPeriod(
        ReservationPeriod reservationPeriod,
        List<ReserveStatus> statuses,
        Long companyId
    );

    @Query("""
        SELECT r FROM CarReservation r
        JOIN Car c ON r.carId = c.id
        WHERE c.company.id = :companyId
        """)
    Page<CarReservation> findCarReservationsByCompanyId(Long companyId, Pageable pageable);

    Page<CarReservation> findCarReservationsByCarId(Long carId, Pageable pageable);
}
