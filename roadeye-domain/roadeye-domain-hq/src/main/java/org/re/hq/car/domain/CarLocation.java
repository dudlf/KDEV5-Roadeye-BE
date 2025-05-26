package org.re.hq.car.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CarLocation {

    @Column(precision = 10, scale = 6)
    private BigDecimal coordinationLat;

    @Column(precision = 9, scale = 6)
    private BigDecimal coordinationLon;

    public CarLocation(BigDecimal coordinationLat, BigDecimal coordinationLon) {
        this.coordinationLat = coordinationLat;
        this.coordinationLon = coordinationLon;
    }
}
