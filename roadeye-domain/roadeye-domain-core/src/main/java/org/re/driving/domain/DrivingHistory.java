package org.re.driving.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.re.driving.converter.DrivingHistoryStatusConverter;
import org.re.mdtlog.converter.TransactionIdConverter;
import org.re.mdtlog.domain.TransactionUUID;

@Getter
@Entity
@Table(name = "car_driving_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DrivingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Convert(converter = DrivingHistoryStatusConverter.class)
    private DrivingHistoryStatus status;

    @Convert(converter = TransactionIdConverter.class)
    private TransactionUUID txUid;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "mileageSum", column = @Column(name = "previous_mileage_sum")),
        @AttributeOverride(name = "datetime", column = @Column(name = "drive_started_at")),
        @AttributeOverride(name = "location.latitude", column = @Column(name = "previous_latitude")),
        @AttributeOverride(name = "location.longitude", column = @Column(name = "previous_longitude"))
    })
    private DrivingSnapShot previousDrivingSnapShot;

    @Nullable
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "mileageSum", column = @Column(name = "next_mileage_sum")),
        @AttributeOverride(name = "datetime", column = @Column(name = "drive_ended_at")),
        @AttributeOverride(name = "location.latitude", column = @Column(name = "next_latitude")),
        @AttributeOverride(name = "location.longitude", column = @Column(name = "next_longitude"))
    })
    private DrivingSnapShot endDrivingSnapShot;

    public DrivingHistory(DrivingHistoryStatus status, TransactionUUID txUid, DrivingSnapShot previousDrivingSnapShot) {
        this.status = status;
        this.txUid = txUid;
        this.previousDrivingSnapShot = previousDrivingSnapShot;
    }

    public static DrivingHistory of(TransactionUUID txUid, DrivingSnapShot drivingSnapShot) {
        return new DrivingHistory(DrivingHistoryStatus.DRIVING, txUid, drivingSnapShot);
    }

    public void end(DrivingSnapShot drivingSnapShot) {
        // TODO do convert Domain Exception
        if (status != DrivingHistoryStatus.DRIVING) {
            throw new IllegalArgumentException("Cannot end DrivingHistory because the status is not DRIVING");
        }
        this.status = DrivingHistoryStatus.ENDED;
        this.endDrivingSnapShot = drivingSnapShot;
    }
}
