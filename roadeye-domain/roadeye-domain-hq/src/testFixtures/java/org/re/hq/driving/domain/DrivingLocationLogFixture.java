package org.re.hq.driving.domain;

import org.re.hq.driving.dto.DrivingLocationLogCreationCommandFixture;
import org.re.hq.reservation.CarReservationFixture;

public class DrivingLocationLogFixture {
    public static DrivingLocationLog create() {
        var reservation = CarReservationFixture.create();
        var cmd = DrivingLocationLogCreationCommandFixture.createFirst(reservation);
        return DrivingLocationLog.create(cmd);
    }
}
