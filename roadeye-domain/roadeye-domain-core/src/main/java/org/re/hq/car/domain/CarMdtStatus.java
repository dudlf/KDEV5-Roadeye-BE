package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.car.converter.CarIgnitionStatusConverter;
import org.re.hq.car.exception.CarDomainException;
import org.re.hq.domain.exception.DomainException;
import org.re.mdtlog.converter.MdtTransactionIdConverter;
import org.re.mdtlog.domain.MdtTransactionId;
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

    @Convert(converter = MdtTransactionIdConverter.class)
    @Column(columnDefinition = "BINARY(16)")
    private MdtTransactionId activeTuid;

    public CarMdtStatus(int batteryVoltage, CarIgnitionStatus ignition, MdtTransactionId activeTuid) {
        this.batteryVoltage = batteryVoltage;
        this.ignition = ignition;
        this.activeTuid = activeTuid;
    }

    public static CarMdtStatus create() {
        return new CarMdtStatus(Integers.ZERO, CarIgnitionStatus.OFF, null);
    }

    public void turnOnIgnition(MdtTransactionId transactionId) {
        if (this.ignition != CarIgnitionStatus.OFF) {
            throw new DomainException(CarDomainException.IGNITION_IS_NOT_OFF);
        }
        this.ignition = CarIgnitionStatus.ON;
        this.activeTuid = transactionId;
    }

    public void turnOffIgnition(MdtTransactionId transactionId) {
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

