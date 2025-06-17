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
import org.re.mdtlog.converter.TransactionIdConverter;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.util.Integers;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarMdtStatus {
    @Column(nullable = false)
    private int batteryVoltage;

    @Convert(converter = CarIgnitionStatusConverter.class)
    @Column(nullable = false)
    private CarIgnitionStatus ignition;

    @Convert(converter = TransactionIdConverter.class)
    @Column(columnDefinition = "BINARY(16)")
    private TransactionUUID activeTuid;

    public CarMdtStatus(int batteryVoltage, CarIgnitionStatus ignition, TransactionUUID activeTuid) {
        this.batteryVoltage = batteryVoltage;
        this.ignition = ignition;
        this.activeTuid = activeTuid;
    }

    public static CarMdtStatus create() {
        return new CarMdtStatus(Integers.ZERO, CarIgnitionStatus.OFF, null);
    }

    public void turnOnIgnition(TransactionUUID transactionId) {
        if (this.ignition != CarIgnitionStatus.OFF) {
            throw new DomainException(CarDomainException.IGNITION_IS_NOT_OFF);
        }
        this.ignition = CarIgnitionStatus.ON;
        this.activeTuid = transactionId;
    }

    public void turnOffIgnition(TransactionUUID transactionId) {
        if (this.ignition != CarIgnitionStatus.ON) {
            throw new DomainException(CarDomainException.IGNITION_IS_NOT_ON);
        }
        if (!this.activeTuid.equals(transactionId)) {
            throw new DomainException(CarDomainException.TRANSACTION_ID_MISMATCH);
        }
        this.ignition = CarIgnitionStatus.OFF;
        this.activeTuid = null;
    }

    public void resetIgnitionStatus() {
        this.ignition = CarIgnitionStatus.OFF;
        this.activeTuid = null;
    }
}

