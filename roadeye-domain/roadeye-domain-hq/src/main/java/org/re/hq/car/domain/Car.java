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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer carId;

    @Column(nullable = false)
    private Integer compId;

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

    @Column(nullable = false)
    private LocalDateTime shippedAt;

    // 생성자
    private Car(Integer compId, String carName, String carImgUrl, String carNumber, Integer mileageSum) {
        this.carName = carName;
        this.carImgUrl = carImgUrl;
        this.carNumber = carNumber;
        this.mileage = new CarMileage(0, mileageSum);
    }

    public static Car of(Integer compId, String carName, String carImgUrl, String carNumber, Integer mileageSum) {
        return new Car(compId, carName, carImgUrl, carNumber, mileageSum);
    }
}

