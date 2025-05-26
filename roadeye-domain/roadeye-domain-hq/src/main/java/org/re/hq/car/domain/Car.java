package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.common.BaseEntity;

import java.time.LocalDateTime;

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
    private CarStatus status;

    @Column(length = 512)
    private String disableReason;

    private LocalDateTime shippedAt;

    // 생성자
    private Car(Long companyId, CarProfile carProfile, int mileageInitial) {
        this.companyId = companyId;
        this.profile = carProfile;
        this.location = CarLocation.createDefault();
        this.mileage = new CarMileage(mileageInitial); // 초기값 세팅
    }

    public static Car of(Long companyId, CarProfile carProfile, int mileageInitial) {
        return new Car(companyId, carProfile, mileageInitial);
    }
}

