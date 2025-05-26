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
public class CarMdtStatus {
    @Column(nullable = false)
    private Integer batteryVoltage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CarIgnitionStatus ignition;

    @Column(columnDefinition = "BINARY(16)")
    private UUID activeTuid;

    public CarMdtStatus(Integer batteryVoltage, CarIgnitionStatus ignition, UUID activeTuid) {
        this.batteryVoltage = batteryVoltage;
        this.ignition = ignition;
        this.activeTuid = activeTuid;
    }

    public static CarMdtStatus createDefault() {
        return new CarMdtStatus(0, CarIgnitionStatus.OFF, null);
    }
}

