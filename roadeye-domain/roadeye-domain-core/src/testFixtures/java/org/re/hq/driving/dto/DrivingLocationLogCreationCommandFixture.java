package org.re.hq.driving.dto;

import org.re.hq.driving.domain.DrivingLocationLog;
import org.re.hq.reservation.domain.CarReservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DrivingLocationLogCreationCommandFixture {
    public static DrivingLocationLogCreationCommand createFirst(CarReservation reservation) {
        var lat = BigDecimal.valueOf(37.5665);
        var lon = BigDecimal.valueOf(126.978);
        var spd = 60;
        DrivingLocationLog prev = null;
        var historyTime = LocalDateTime.now();
        return new DrivingLocationLogCreationCommand(reservation, lat, lon, spd, prev, historyTime);
    }

    public static DrivingLocationLogCreationCommand create(CarReservation carReservation, DrivingLocationLog prev) {
        var lat = BigDecimal.valueOf(37.5665 + Math.random() * 0.01);
        var lon = BigDecimal.valueOf(126.978 + Math.random() * 0.01);
        var spd = (int) (Math.random() * 100);
        var historyTime = LocalDateTime.now().plusMinutes((long) (Math.random() * 60));
        return new DrivingLocationLogCreationCommand(carReservation, lat, lon, spd, prev, historyTime);
    }
}
