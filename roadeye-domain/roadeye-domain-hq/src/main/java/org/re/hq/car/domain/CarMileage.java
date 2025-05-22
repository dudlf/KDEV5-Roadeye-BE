package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarMileage {

    @Column(nullable = false)
    private Integer mileageInit = 0;

    @Column(nullable = false)
    private Integer mileageSum;

    public CarMileage(Integer mileageInit) {
        this.mileageInit = mileageInit;
        this.mileageSum = mileageInit;
    }
}
