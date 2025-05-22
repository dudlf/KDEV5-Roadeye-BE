package org.re.hq.car.domain;

import jakarta.persistence.*;
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
    private Integer companyId;

    @Column(nullable = false, length = 30)
    private String carName;

    @Column(length = 512)
    private String carImgUrl;

    @Column(nullable = false, length = 8)
    private String carNumber;

    @Embedded
    private CarLocation location;

    @Embedded
    private CarMileage mileage;

    @Embedded
    private CarStatus status;

    private LocalDateTime disabledAt;

    @Column(length = 512)
    private String disableReason;

    private LocalDateTime shippedAt;

    // 생성자
    private Car(Integer companyId, String carName, String carImgUrl, String carNumber, Integer mileageInit) {
        this.companyId = companyId;
        this.carName = carName;
        this.carImgUrl = carImgUrl;
        this.carNumber = carNumber;
        this.mileage = new CarMileage(mileageInit); // 초기값 세팅
    }

    public static Car of(Integer companyId, String carName, String carImgUrl, String carNumber, Integer mileageInit) {
        return new Car(companyId, carName, carImgUrl, carNumber, mileageInit);
    }
}

