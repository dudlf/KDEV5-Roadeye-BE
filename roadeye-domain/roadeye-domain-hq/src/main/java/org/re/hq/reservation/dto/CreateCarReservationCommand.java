package org.re.hq.reservation.dto;

import org.re.hq.car.domain.Car;
import org.re.hq.employee.domain.Employee;
import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.domain.ReservationPeriod;
import org.re.hq.reservation.domain.ReserveReason;

import java.time.LocalDateTime;

public record CreateCarReservationCommand(
    Long companyId,
    Car car,
    Employee reserver,
    ReservationPeriod reservationPeriod,
    ReserveReason reserveReason,
    LocalDateTime reservedAt
) {
    public CarReservation toEntity(){
        return CarReservation.createReservation(
            companyId,
            car,
            reserver,
            reservationPeriod,
            reserveReason,
            reservedAt
        );
    }
}
