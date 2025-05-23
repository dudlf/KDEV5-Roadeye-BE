package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarStatus {

    private Integer batteryVoltage;

    @Enumerated(EnumType.STRING)
    private CarIgnitionStatus ignition;

    @Column(columnDefinition = "BINARY(16)")
    private UUID activeTuid;

    public CarStatus(Integer batteryVoltage, CarIgnitionStatus ignition, UUID activeTuid) {
        this.batteryVoltage = batteryVoltage;
        this.ignition = ignition;
        this.activeTuid = activeTuid;
    }
}

