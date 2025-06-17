package org.re.driving.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
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
    @Convert(converter = org.re.driving.domain.DrivingHistoryStatusConverter.class)
    private org.re.driving.domain.DrivingHistoryStatus status;

    @Convert(converter = TransactionIdConverter.class)
    private TransactionUUID txUid;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "mileageSum", column = @Column(name = "previous_mileage_sum")),
        @AttributeOverride(name = "datetime", column = @Column(name = "drive_started_at")),
        @AttributeOverride(name = "location.latitude", column = @Column(name = "previous_latitude")),
        @AttributeOverride(name = "location.longitude", column = @Column(name = "previous_longitude"))
    })
    private org.re.driving.domain.DrivingSnapShot previousDrivingSnapShot;

    @Nullable
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "mileageSum", column = @Column(name = "next_mileage_sum")),
        @AttributeOverride(name = "datetime", column = @Column(name = "drive_ended_at")),
        @AttributeOverride(name = "location.latitude", column = @Column(name = "next_latitude")),
        @AttributeOverride(name = "location.longitude", column = @Column(name = "next_longitude"))
    })
    private DrivingSnapShot endDrivingSnapShot;

    public DrivingHistory(org.re.driving.domain.DrivingHistoryStatus status, TransactionUUID txUid, org.re.driving.domain.DrivingSnapShot previousDrivingSnapShot) {
        this.status = status;
        this.txUid = txUid;
        this.previousDrivingSnapShot = previousDrivingSnapShot;
    }

    public static DrivingHistory of(TransactionUUID txUid, org.re.driving.domain.DrivingSnapShot drivingSnapShot) {
        return new DrivingHistory(org.re.driving.domain.DrivingHistoryStatus.DRIVING, txUid, drivingSnapShot);
    }

    public void end(org.re.driving.domain.DrivingSnapShot drivingSnapShot) {
        // TODO do convert Domain Exception
        if (status != org.re.driving.domain.DrivingHistoryStatus.DRIVING) {
            throw new IllegalArgumentException("Cannot end DrivingHistory because the status is not DRIVING");
        }
        this.status = org.re.driving.domain.DrivingHistoryStatus.ENDED;
        this.endDrivingSnapShot = drivingSnapShot;
    }
}
