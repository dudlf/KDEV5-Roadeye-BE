package org.re.hq.location.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.re.hq.car.domain.CarLocation;

import java.time.LocalDateTime;

@Entity
@Table(name = "car_location_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LocationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long drivingId;

    @Embedded
    private CarLocation carLocation;

    @Embedded
    private DrivingMoment drivingMoment;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    public LocationHistory(Long drivingId, CarLocation carLocation, DrivingMoment drivingMoment) {
        this.drivingId = drivingId;
        this.carLocation = carLocation;
        this.drivingMoment = drivingMoment;
    }
}
