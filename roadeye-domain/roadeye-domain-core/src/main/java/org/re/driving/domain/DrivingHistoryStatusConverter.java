package org.re.driving.domain;

import jakarta.persistence.Converter;
import org.re.common.domain.AbstractPersistenceEnumConverter;

@Converter(autoApply = true)
public class DrivingHistoryStatusConverter extends AbstractPersistenceEnumConverter<org.re.driving.domain.DrivingHistoryStatus, String> {
    public DrivingHistoryStatusConverter() {
        super(org.re.driving.domain.DrivingHistoryStatus.class);
    }
}
