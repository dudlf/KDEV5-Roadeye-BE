package org.re.hq.reservation.dto;

import org.re.hq.car.domain.Car;
import org.re.hq.employee.domain.Employee;
import org.re.hq.reservation.domain.ReservationPeriod;
import org.re.hq.reservation.domain.ReserveReason;

import java.time.LocalDateTime;

public record CarReservationCreateRequest(
        Long carId,
        LocalDateTime rentStartAt,
        LocalDateTime rentEndAt,
        ReserveReason reserveReason,
        LocalDateTime reservedAt
) {
    public CreateCarReservationCommand toCommand(Long companyId, Car car, Employee reserver){
        return new CreateCarReservationCommand(
            companyId,
            car,
            reserver,
            ReservationPeriod.of(rentStartAt, rentEndAt),
            reserveReason,
            reservedAt
        );
    }
}
