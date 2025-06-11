package org.re.hq.reservation.service;

import lombok.RequiredArgsConstructor;
import org.re.hq.car.domain.Car;
import org.re.hq.domain.common.DomainService;
import org.re.hq.domain.exception.DomainException;
import org.re.hq.employee.domain.Employee;
import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.domain.CarReservationRepository;
import org.re.hq.reservation.domain.ReservationPeriod;
import org.re.hq.reservation.domain.ReserveStatus;
import org.re.hq.reservation.dto.CreateCarReservationCommand;
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
    public CarReservation approveReservation(Long reservationId, Employee approver, LocalDateTime processedAt) {
        CarReservation reservation = carReservationRepository.findById(reservationId)
            .orElseThrow(() -> new DomainException(CarReservationDomainException.RESERVATION_NOT_FOUND));

        reservation.approve(approver, processedAt);
        return reservation;
    }

    /**
     * 예약 반려 처리
     */
    public CarReservation rejectReservation(Long reservationId, Employee approver, String rejectReason, LocalDateTime processedAt) {
        CarReservation reservation = carReservationRepository.findById(reservationId)
            .orElseThrow(() -> new DomainException(CarReservationDomainException.RESERVATION_NOT_FOUND));

        reservation.reject(approver, rejectReason, processedAt);
        return reservation;
    }

    /**
     * 예약 등록
     */

    public CarReservation createReservation(CreateCarReservationCommand command) {
        checkReservation(command.companyId(), command.car(), command.reservationPeriod());

        CarReservation carReservation = command.toEntity();

        return carReservationRepository.save(carReservation);
    }

    /**
     * 차량은 동일한 시간대에 두 개 이상의 예약을 가질 수 없다.
     */
    private void checkReservation(Long companyId, Car car, ReservationPeriod reservationPeriod) {
        boolean exists = carReservationRepository.existsCarReservationsByReservationPeriodContaining(
            car.getId(),
            List.of(ReserveStatus.APPROVED, ReserveStatus.REQUESTED),
            reservationPeriod,
            companyId
        );

        if (exists) {
            throw new DomainException(CarReservationDomainException.RESERVATION_ALREADY_EXISTS);
        }
    }


    @Transactional(readOnly = true)
    public CarReservation findByIdAndCompanyId(Long reservationId, Long companyId) {
        return carReservationRepository.findByIdAndCompanyId(reservationId, companyId)
            .orElseThrow(() -> new DomainException(CarReservationDomainException.RESERVATION_NOT_FOUND));
    }

    /**
     * 차량, 현재시간이 일치하는 예약 번호 찾기
     */
    public Long findReservationId(Car car, LocalDateTime ignitionAt) {
        return carReservationRepository.findIdByCarIdAndIgnitionAt(car.getId(), ignitionAt)
            .orElseThrow(() -> new DomainException(CarReservationDomainException.RESERVATION_NOT_FOUND));
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

    @Transactional(readOnly = true)
    public Page<CarReservation> findByEmployeeId(Long employeeId, Pageable pageable) {
        return carReservationRepository.findAllByReserverId(employeeId, pageable);
    }
}
