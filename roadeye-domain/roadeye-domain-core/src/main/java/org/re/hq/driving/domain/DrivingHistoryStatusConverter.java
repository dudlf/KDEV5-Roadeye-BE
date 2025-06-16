package org.re.hq.driving.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DrivingHistoryStatusConverter implements AttributeConverter<DrivingHistoryStatus, String> {

    @Override
    public String convertToDatabaseColumn(DrivingHistoryStatus drivingHistoryStatus) {
        return drivingHistoryStatus.getValue();
    }

    @Override
    public DrivingHistoryStatus convertToEntityAttribute(String s) {
        return DrivingHistoryStatus.from(s);
    }
}
