package org.re.hq.reservation.exception;

import lombok.Getter;
import org.re.hq.domain.exception.DomainExceptionCode;

@Getter
public enum CarReservationDomainException implements DomainExceptionCode {
    // @formatter:off
    RESERVATION_NOT_FOUND     ("100", "Reservation not found."),
    RESERVATION_ALREADY_EXISTS("101", "Reservation already exists."),
    RESERVATION_STATUS_IS_NOT_REQUESTED("102", "Reservation status is not requested."),
    RENT_START_TIME_INVALID ("103", "Rent start time is invalid."),
    RENT_END_TIME_INVALID   ("104", "Rent end time is invalid."),
    ;
    // @formatter:on

    private static final String PREFIX = "RSV_";

    private final String code;
    private final String message;

    CarReservationDomainException(String code, String message) {
        this.code = PREFIX + code;
        this.message = message;
    }
}
