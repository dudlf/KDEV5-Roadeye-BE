package org.re.hq.reservation.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.exception.DomainException;
import org.re.hq.reservation.exception.CarReservationDomainException;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationPeriod {
    private LocalDateTime rentStartAt;

    private LocalDateTime rentEndAt;

    private ReservationPeriod(LocalDateTime rentStartAt, LocalDateTime rentEndAt) {
        if (rentStartAt.isBefore(LocalDateTime.now())) {
            throw new DomainException(CarReservationDomainException.RENT_START_TIME_INVALID);
        }

        if (!rentEndAt.isAfter(rentStartAt)) {
            throw new DomainException(CarReservationDomainException.RENT_END_TIME_INVALID);
        }

        this.rentStartAt = rentStartAt;
        this.rentEndAt = rentEndAt;
    }

    public static ReservationPeriod of(LocalDateTime rentStartAt, LocalDateTime rentEndAt) {
        return new ReservationPeriod(rentStartAt, rentEndAt);
    }
}
