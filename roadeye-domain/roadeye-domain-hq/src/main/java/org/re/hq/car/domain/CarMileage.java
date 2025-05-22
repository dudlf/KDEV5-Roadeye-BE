package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class CarMileage {

    @Column(nullable = false)
    private Integer mileageInit = 0;

    @Column(nullable = false)
    private Integer mileageSum;

    public CarMileage(Integer mileageInit, Integer mileageSum) {
        this.mileageInit = mileageInit;
        this.mileageSum = mileageSum;
    }
}
