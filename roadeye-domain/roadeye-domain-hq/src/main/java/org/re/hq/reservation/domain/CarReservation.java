package org.re.hq.reservation.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.exception.DomainException;
import org.re.hq.reservation.exception.CarReservationDomainException;
import org.re.hq.car.domain.Car;
import org.re.hq.domain.common.BaseEntity;
import org.re.hq.employee.domain.Employee;


import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarReservation extends BaseEntity {

    @Column(nullable = false)
    private Long companyId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Car car;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Employee reserver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Employee approver;

    @Embedded
    private ReservationPeriod reservationPeriod;

    @Enumerated(EnumType.STRING)
    private ReserveStatus reserveStatus;

    @Enumerated(EnumType.STRING)
    private ReserveReason reserveReason;

    private LocalDateTime reservedAt;

    private LocalDateTime processedAt;

    private String rejectReason;

    private CarReservation(Long companyId, Car car, Employee reserver, ReservationPeriod reservationPeriod,
                           ReserveReason reserveReason, LocalDateTime reservedAt) {
        this.companyId = companyId;
        this.car = car;
        this.reserver = reserver;
        this.reservationPeriod = reservationPeriod;
        this.reserveStatus = ReserveStatus.REQUESTED;
        this.reserveReason = reserveReason;
        this.reservedAt = reservedAt;
    }

    public static CarReservation createReservation(Long companyId, Car car, Employee reserver, ReservationPeriod reservationPeriod,
                                                   ReserveReason reserveReason, LocalDateTime reservedAt) {
        return new CarReservation(companyId, car, reserver, reservationPeriod, reserveReason, reservedAt);
    }

    public void approve(Employee approver, LocalDateTime processedAt) {
        if (!this.reserveStatus.isRequested()) {
            throw new DomainException(CarReservationDomainException.RESERVATION_STATUS_IS_NOT_REQUESTED);
        }
        this.reserveStatus = ReserveStatus.APPROVED;
        this.approver = approver;
        this.processedAt = processedAt;
    }

    public void reject(Employee approver, String rejectReason, LocalDateTime processedAt) {
        if (!this.reserveStatus.isRequested()) {
            throw new DomainException(CarReservationDomainException.RESERVATION_STATUS_IS_NOT_REQUESTED);
        }
        this.reserveStatus = ReserveStatus.REJECTED;
        this.approver = approver;
        this.rejectReason = rejectReason;
        this.processedAt = processedAt;
    }
}
