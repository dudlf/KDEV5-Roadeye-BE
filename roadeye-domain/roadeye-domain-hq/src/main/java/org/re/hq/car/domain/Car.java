package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.common.BaseEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Car extends BaseEntity {

    @Column(nullable = false)
    private Long companyId;

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

    private Car(Long companyId, CarProfile carProfile, int mileageInitial) {
        this.companyId = companyId;
        this.profile = carProfile;
        this.location = CarLocation.createDefault();
        this.mdtStatus = CarMdtStatus.createDefault();
        this.mileage = new CarMileage(mileageInitial);
    }

    public static Car of(Long companyId, CarProfile carProfile, int mileageInitial) {
        return new Car(companyId, carProfile, mileageInitial);
    }

    public void disable(String reason) {
        this.disableReason = reason;
        super.disable();
    }

    public void turnOnIgnition(UUID transactionId) {
        this.mdtStatus.turnOnIgnition(transactionId);
    }

    public void turnOffIgnition(UUID transactionId) {
        this.mdtStatus.turnOffIgnition(transactionId);
    }
}

