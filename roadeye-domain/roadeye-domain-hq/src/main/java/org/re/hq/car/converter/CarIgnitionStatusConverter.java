package org.re.hq.car.converter;

import jakarta.persistence.Converter;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.hq.domain.common.AbstractPersistenceEnumConverter;

@Converter
public class CarIgnitionStatusConverter extends AbstractPersistenceEnumConverter<CarIgnitionStatus, Integer> {
    public CarIgnitionStatusConverter() {
        super(CarIgnitionStatus.class);
    }
}
