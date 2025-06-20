package org.re.car.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.re.car.dto.CarUpdateCommand;
import org.re.common.domain.BaseEntity;
import org.re.company.domain.Company;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.mdtlog.dto.MdtIgnitionOffMessage;
import org.re.mdtlog.dto.MdtIgnitionOnMessage;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, updatable = false)
    private Company company;

    @Embedded
    private CarProfile profile;

    @Embedded
    private CarLocation location;

    @Embedded
    private CarMileage mileage;

    @Embedded
    private CarMdtStatus mdtStatus;

    @Column(length = 512)
    private String disableReason;

    private LocalDateTime shippedAt;

    private Car(Company company, CarProfile carProfile, int mileageInitial) {
        this.company = company;
        this.profile = carProfile;
        this.location = CarLocation.create();
        this.mdtStatus = CarMdtStatus.create();
        this.mileage = new CarMileage(mileageInitial);
    }

    public static Car of(Company company, CarProfile carProfile, int mileageInitial) {
        return new Car(company, carProfile, mileageInitial);
    }

    public void disable(String reason) {
        this.disableReason = reason;
        super.disable();
    }

    public void turnOnIgnition(TransactionUUID transactionUUID) {
        this.mdtStatus.turnOnIgnition(transactionUUID);
    }

    public void turnOnIgnition(MdtEventMessage<MdtIgnitionOnMessage> message) {
        var payload = message.payload();
        this.location = payload.getLocation();
        this.mdtStatus.turnOnIgnition(message.transactionId());
        this.mdtStatus.setAngle(payload.mdtAngle());
        this.mdtStatus.setSpeed(payload.mdtSpeed());
        this.mdtStatus.setIgnitionOnTime(payload.ignitionOnTime());
        this.mileage.setTotal(payload.mdtMileageSum());
    }

    public void turnOffIgnition(TransactionUUID transactionUUID) {
        this.mdtStatus.turnOffIgnition(transactionUUID);
    }

    public void turnOffIgnition(MdtEventMessage<MdtIgnitionOffMessage> message) {
        var payload = message.payload();
        this.mdtStatus.turnOffIgnition(message.transactionId());
        this.mdtStatus.setAngle(payload.mdtAngle());
        this.mdtStatus.setSpeed(payload.mdtSpeed());
        this.mdtStatus.setIgnitionOnTime(payload.ignitionOnTime());
        this.mdtStatus.setIgnitionOffTime(payload.ignitionOffTime());
        this.mileage.setTotal(payload.mdtMileageSum());
    }

    public void resetIgnitionStatus() {
        this.mdtStatus.resetIgnitionStatus();
    }

    public void update(CarUpdateCommand command) {
        if (command.name() != null) {
            profile.setName(command.name());
        }
        if (command.imageUrl() != null) {
            profile.setImageUrl(command.imageUrl());
        }
    }

    public void updateLocation(@NonNull CarLocation lastLocation) {
        this.location = lastLocation;
    }
}

