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
    private int initial = 0;

    @Column(nullable = false)
    private int total;

    public CarMileage(int initial) {
        this.initial = initial;
        this.total = initial;
    }
}
