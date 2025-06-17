package org.re.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.car.converter.CarIgnitionStatusConverter;
import org.re.car.exception.CarDomainException;
import org.re.common.exception.DomainException;
import org.re.util.Integers;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarMdtStatus {
    @Column(nullable = false)
    private int batteryVoltage;

    @Convert(converter = CarIgnitionStatusConverter.class)
    @Column(nullable = false)
    private org.re.car.domain.CarIgnitionStatus ignition;

    @Column(columnDefinition = "BINARY(16)")
    private UUID activeTuid;

    public CarMdtStatus(int batteryVoltage, org.re.car.domain.CarIgnitionStatus ignition, UUID activeTuid) {
        this.batteryVoltage = batteryVoltage;
        this.ignition = ignition;
        this.activeTuid = activeTuid;
    }

    public static CarMdtStatus create() {
        return new CarMdtStatus(Integers.ZERO, org.re.car.domain.CarIgnitionStatus.OFF, null);
    }

    public void turnOnIgnition(UUID transactionId) {
        if (this.ignition != org.re.car.domain.CarIgnitionStatus.OFF) {
            throw new DomainException(CarDomainException.IGNITION_IS_NOT_OFF);
        }
        this.ignition = org.re.car.domain.CarIgnitionStatus.ON;
        this.activeTuid = transactionId;
    }

    public void turnOffIgnition(UUID transactionId) {
        if (this.ignition != org.re.car.domain.CarIgnitionStatus.ON) {
            throw new DomainException(CarDomainException.IGNITION_IS_NOT_ON);
        }
        if (!this.activeTuid.equals(transactionId)) {
            throw new DomainException(CarDomainException.TRANSACTION_ID_MISMATCH);
        }
        this.ignition = org.re.car.domain.CarIgnitionStatus.OFF;
        this.activeTuid = null;
    }

    public void resetIgnitionStatus() {
        this.ignition = org.re.car.domain.CarIgnitionStatus.OFF;
        this.activeTuid = null;
    }
}

