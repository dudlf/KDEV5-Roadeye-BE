package org.re.hq.reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.common.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarReservation extends BaseEntity {

    private Long carId;

    private Long reserverId;

    private Long approverId;

    private LocalDateTime rentStartAt;

    private LocalDateTime rentEndAt;

    @Enumerated(EnumType.STRING)
    private ReserveStatus reserveStatus;

    @Enumerated(EnumType.STRING)
    private ReserveReason reserveReason;

    private LocalDateTime reservedAt;

    private LocalDateTime approvedAt;

    private LocalDateTime rejectedAt;

    private String rejectReason;

    private CarReservation(Long carId, Long reserverId, LocalDateTime rentStartAt, LocalDateTime rentEndAt,
                           ReserveReason reserveReason) {
        this.carId = carId;
        this.reserverId = reserverId;
        this.rentStartAt = rentStartAt;
        this.rentEndAt = rentEndAt;
        this.reserveStatus = ReserveStatus.REQUESTED;
        this.reserveReason = reserveReason;
        this.reservedAt = LocalDateTime.now();
    }

    public static CarReservation createReservation(Long carId, Long reserverId, LocalDateTime rentStartAt,
                                                   LocalDateTime rentEndAt, ReserveReason reserveReason) {
        return new CarReservation(carId, reserverId, rentStartAt, rentEndAt, reserveReason);
    }
}
