package org.re.hq.reservation.dto;

import org.re.hq.reservation.domain.CarReservation;
import org.re.hq.reservation.domain.ReserveReason;
import org.re.hq.reservation.domain.ReserveStatus;

import java.time.LocalDateTime;

public record CarReservationResponse(
    Long reservationId,
    String carName,
    String licenseNumber,
    String reserverName,
    LocalDateTime rentStartAt,
    LocalDateTime rentEndAt,
    ReserveStatus reserveStatus,
    ReserveReason reserveReason,
    String approverName,
    String rejectReason
) {
    public static CarReservationResponse from(CarReservation carReservation) {
        return new CarReservationResponse(
            carReservation.getId(),
            carReservation.getCar().getProfile().getName(),
            carReservation.getCar().getProfile().getLicenseNumber(),
            carReservation.getReserver().getMetadata().getName(),
            carReservation.getReservationPeriod().getRentStartAt(),
            carReservation.getReservationPeriod().getRentEndAt(),
            carReservation.getReserveStatus(),
            carReservation.getReserveReason(),
            carReservation.getReserveStatus() != ReserveStatus.REQUESTED
            ? carReservation.getApprover().getMetadata().getName() : null,
            carReservation.getReserveStatus() == ReserveStatus.REJECTED
            ? carReservation.getRejectReason() : null
        );
    }
}
