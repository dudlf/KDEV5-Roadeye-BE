package org.re.hq.reservation.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.domain.common.DomainService;
import org.re.hq.domain.exception.DomainException;
import org.re.hq.reservation.domain.*;
import org.re.hq.reservation.exception.CarReservationDomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@DomainService
@Transactional
@RequiredArgsConstructor
public class CarReservationDomainService {
    private final CarReservationRepository carReservationRepository;

    /**
     * 예약 승인 처리
     */
    public void approveReservation(Long reservationId, Long approverId, LocalDateTime processedAt) {
        CarReservation reservation = carReservationRepository.findById(reservationId)
            .orElseThrow(() -> new DomainException(CarReservationDomainException.RESERVATION_NOT_FOUND));

        reservation.approve(approverId, processedAt);
    }

    /**
     * 예약 반려 처리
     */
    public void rejectReservation(Long reservationId, Long approverId, String rejectReason, LocalDateTime processedAt) {
        CarReservation reservation = carReservationRepository.findById(reservationId)
            .orElseThrow(() -> new DomainException(CarReservationDomainException.RESERVATION_NOT_FOUND));

        reservation.reject(approverId, rejectReason, processedAt);
    }

    /**
     * 예약 등록
     */
    public void createReservation(Long companyId, Long carId, Long reserverId, ReservationPeriod reservationPeriod,
                                  ReserveReason reserveReason, LocalDateTime reservedAt) {
        checkReservation(companyId, carId, reservationPeriod);

        CarReservation carReservation = CarReservation.createReservation(companyId, carId, reserverId, reservationPeriod, reserveReason, reservedAt);

        carReservationRepository.save(carReservation);
    }

    /**
     * 차량은 동일한 시간대에 두 개 이상의 예약을 가질 수 없다.
     */
    private void checkReservation(Long companyId, Long carId, ReservationPeriod reservationPeriod) {
        boolean exists = carReservationRepository.existsCarReservationsByReservationPeriodContaining(
            carId,
            List.of(ReserveStatus.APPROVED, ReserveStatus.REQUESTED),
            reservationPeriod,
            companyId
        );

        if (exists) {
            throw new DomainException(CarReservationDomainException.RESERVATION_ALREADY_EXISTS);
        }
    }

    /**
     * 해당 시간대에 예약이 있는 차량 리스트 반환
     */
    @Transactional(readOnly = true)
    public List<Long> findCarIdsWithReservationPeriod(Long companyId, ReservationPeriod reservationPeriod) {
        return carReservationRepository.findCarIdsWithReservationPeriod(
            reservationPeriod,
            List.of(ReserveStatus.APPROVED, ReserveStatus.REQUESTED),
            companyId
        );
    }

    /**
     * 예약목록
     */
    @Transactional(readOnly = true)
    public Page<CarReservation> findReservationWithCompanyId(Long companyId, Pageable pageable) {
        return carReservationRepository.findCarReservationsByCompanyId(companyId, pageable);
    }

    /**
     * 차량별 예약목록
     */
    @Transactional(readOnly = true)
    public Page<CarReservation> findReservationWithCarId(Long carId, Pageable pageable) {
        return carReservationRepository.findCarReservationsByCarId(
            carId,
            pageable
        );
    }
}
