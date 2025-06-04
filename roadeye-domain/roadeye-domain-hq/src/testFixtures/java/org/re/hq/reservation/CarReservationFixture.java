package org.re.hq.reservation;

import org.re.hq.car.CarFixture;
import org.re.hq.car.domain.Car;
import org.re.hq.company.CompanyFixture;
import org.re.hq.employee.domain.Employee;
import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.domain.ReservationPeriod;
import org.re.hq.reservation.domain.ReserveReason;

import java.time.LocalDateTime;

public class CarReservationFixture {
    public static CarReservation create(Car car, Employee reserver, int start, int end) {
        return CarReservation.createReservation(
            car.getCompany().getId(),
            car,
            reserver,
            ReservationPeriod.of(LocalDateTime.now().plusDays(start), LocalDateTime.now().plusDays(end)),
            ReserveReason.BUSINESS_TRIP,
            LocalDateTime.now()
        );
    }
}
