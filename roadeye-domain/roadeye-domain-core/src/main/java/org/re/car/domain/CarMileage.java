package org.re.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarMileage {

    @Column(name = "mileage_initial", nullable = false)
    private int initial;

    @Setter(AccessLevel.PACKAGE)
    @Column(name = "mileage_sum", nullable = false)
    private int total;

    public CarMileage(int initial) {
        this.initial = initial;
        this.total = initial;
    }
}
