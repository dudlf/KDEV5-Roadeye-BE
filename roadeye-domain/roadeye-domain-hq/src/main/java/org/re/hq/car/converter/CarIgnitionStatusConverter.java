package org.re.hq.car.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.re.hq.car.domain.CarIgnitionStatus;

@Converter(autoApply = true)
public class CarIgnitionStatusConverter implements AttributeConverter<CarIgnitionStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(CarIgnitionStatus attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getValue();
    }

    @Override
    public CarIgnitionStatus convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return CarIgnitionStatus.of(dbData);
    }
}
