package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarStatus {

    private Integer batteryVoltage;

    private Integer ignitionStatus;

    @Column(columnDefinition = "BINARY(16)")
    private UUID activeTuid;

    public CarStatus(Integer batteryVoltage, Integer ignitionStatus, UUID activeTuid) {
        this.batteryVoltage = batteryVoltage;
        this.ignitionStatus = ignitionStatus;
        this.activeTuid = activeTuid;
    }
}

