package org.re.hq.reservation.fixture;

import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.domain.ReservationPeriod;
import org.re.hq.reservation.domain.ReserveReason;

import java.time.LocalDateTime;
import java.util.List;

public class CarReservationFixture {

    public static CarReservation createReservation(Long carId, int start, int end) {
        return CarReservation.createReservation(
            carId,
            10L,
            ReservationPeriod.of(LocalDateTime.now().plusDays(start), LocalDateTime.now().plusDays(end)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        );
    }

    public static List<CarReservation> createReservations() {
        return List.of(
            CarReservationFixture.createReservation(2L,1,5),
            CarReservationFixture.createReservation(3L,3,5),
            CarReservationFixture.createReservation(4L,10,12)
        );
    }
}
