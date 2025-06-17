package org.re.hq.driving.domain;

import jakarta.persistence.Embeddable;
import org.re.car.domain.CarLocation;

import java.time.LocalDateTime;

@Embeddable
public record DrivingSnapShot(
    int mileageSum,
    CarLocation location,
    LocalDateTime datetime
) {
}
