package org.re.hq.driving.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.car.domain.CarLocation;
import org.re.hq.driving.dto.DrivingLocationLogCreationCommand;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingLocationLog {
    @EmbeddedId
    private DrivingLocationLogId id;

    @Embedded
    private CarLocation location;

    @Column(nullable = false, updatable = false)
    private Duration driveTime;

    @Column(nullable = false, updatable = false)
    private int carSpeed;

    @Column(nullable = false, updatable = false)
    private LocalDateTime historyTime;

    public static DrivingLocationLog create(DrivingLocationLogCreationCommand command) {
        if (command.prev() == null) {
            return createFirstHistory(command);
        }

        var prevSeq = command.prev().getId().getSequence();
        var id = new DrivingLocationLogId(command.carReservation(), prevSeq + 1);
        var driveTime = Duration.between(command.prev().getHistoryTime(), command.historyTime());
        return new DrivingLocationLog(
            id,
            new CarLocation(command.latitude(), command.longitude()),
            driveTime,
            command.carSpeed(),
            command.historyTime()
        );
    }

    private static DrivingLocationLog createFirstHistory(
        DrivingLocationLogCreationCommand command
    ) {
        var id = new DrivingLocationLogId(command.carReservation(), 1L);
        var driveTime = Duration.ZERO;
        return new DrivingLocationLog(
            id,
            new CarLocation(command.latitude(), command.longitude()),
            driveTime,
            0,
            command.historyTime()
        );
    }
}
