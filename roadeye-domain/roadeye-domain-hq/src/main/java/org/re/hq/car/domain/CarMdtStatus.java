package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.car.converter.CarIgnitionStatusConverter;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarMdtStatus {
    @Column(nullable = false)
    private Integer batteryVoltage;

    @Convert(converter = CarIgnitionStatusConverter.class)
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

    public void turnOnIgnition(UUID transactionId) {
        if (this.ignition != CarIgnitionStatus.OFF) {
            throw new IllegalStateException("Ignition is already ON or in an invalid state.");
        }
        this.ignition = CarIgnitionStatus.ON;
        this.activeTuid = transactionId;
    }
}

