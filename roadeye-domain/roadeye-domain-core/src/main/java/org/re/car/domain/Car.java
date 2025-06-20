package org.re.car.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.car.dto.CarUpdateCommand;
import org.re.common.domain.BaseEntity;
import org.re.company.domain.Company;
import org.re.mdtlog.domain.MdtLogGpsCondition;
import org.re.mdtlog.dto.MdtCycleLogMessage;
import org.re.mdtlog.dto.MdtEventMessage;
import org.re.mdtlog.dto.MdtIgnitionOffMessage;
import org.re.mdtlog.dto.MdtIgnitionOnMessage;

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
    private CarMdtStatus mdtStatus;

    @Column(length = 512)
    private String disableReason;

    private Car(Company company, CarProfile carProfile) {
        this.company = company;
        this.profile = carProfile;
        this.mdtStatus = CarMdtStatus.create();
    }

    public static Car of(Company company, CarProfile carProfile) {
        return new Car(company, carProfile);
    }

    public void disable(String reason) {
        this.disableReason = reason;
        super.disable();
    }

    public void turnOnIgnition(MdtEventMessage<MdtIgnitionOnMessage> message) {
        var payload = message.payload();
        this.mdtStatus.setLocation(payload.getLocation());
        this.mdtStatus.turnOnIgnition(message.transactionId());
        this.mdtStatus.setAngle(payload.mdtAngle());
        this.mdtStatus.setSpeed(payload.mdtSpeed());
        this.mdtStatus.setIgnitionOnTime(payload.ignitionOnTime());
        this.mdtStatus.setMileageSum(payload.mdtMileageSum());
        switch (payload.gpsCondition()) {
            case NORMAL, NOT_ATTACHED -> {
                this.mdtStatus.setGpsCondition(payload.gpsCondition());
            }
            default -> {
                // ??
            }
        }
    }

    public void turnOffIgnition(MdtEventMessage<MdtIgnitionOffMessage> message) {
        var payload = message.payload();
        this.mdtStatus.turnOffIgnition(message.transactionId());
        this.mdtStatus.setAngle(payload.mdtAngle());
        this.mdtStatus.setSpeed(payload.mdtSpeed());
        this.mdtStatus.setIgnitionOnTime(payload.ignitionOnTime());
        this.mdtStatus.setIgnitionOffTime(payload.ignitionOffTime());
        switch (payload.gpsCondition()) {
            case NORMAL, NOT_ATTACHED -> {
                this.mdtStatus.setGpsCondition(payload.gpsCondition());
            }
            case INVALID -> {
                this.mdtStatus.setGpsCondition(MdtLogGpsCondition.GPS_INVALID_AT_KEY_OFF);
            }
            case GPS_INVALID_AT_KEY_OFF -> {
                // ??
            }
        }
        this.mdtStatus.setMileageSum(payload.mdtMileageSum());
    }

    public void updateMdtStatus(MdtEventMessage<MdtCycleLogMessage> message) {
        var lastEvent = message.payload().cycleLogList().getLast();

        this.mdtStatus.setLocation(lastEvent.toCarLocation());
        switch (lastEvent.gpsCondition()) {
            case NORMAL, INVALID, NOT_ATTACHED -> {
                this.mdtStatus.setGpsCondition(lastEvent.gpsCondition());
            }
            case GPS_INVALID_AT_KEY_OFF -> {
                // ??
            }
        }
        this.mdtStatus.setAngle(lastEvent.mdtAngle());
        this.mdtStatus.setSpeed(lastEvent.mdtSpeed());
        this.mdtStatus.setBatteryVoltage(lastEvent.batteryVoltage());
        this.mdtStatus.setMileageSum(lastEvent.mdtMileageSum());
    }

    public void update(CarUpdateCommand command) {
        if (command.name() != null) {
            profile.setName(command.name());
        }
        if (command.imageUrl() != null) {
            profile.setImageUrl(command.imageUrl());
        }
    }
}

