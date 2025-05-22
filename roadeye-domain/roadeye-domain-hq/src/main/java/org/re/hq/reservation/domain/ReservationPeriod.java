package org.re.hq.reservation.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationPeriod {
    private LocalDateTime rentStartAt;

    private LocalDateTime rentEndAt;

    private ReservationPeriod(LocalDateTime rentStartAt, LocalDateTime rentEndAt) {
        this.rentStartAt = rentStartAt;
        this.rentEndAt = rentEndAt;
    }

    public static ReservationPeriod of(LocalDateTime rentStartAt, LocalDateTime rentEndAt) {
        return new ReservationPeriod(rentStartAt, rentEndAt);
    }
}
