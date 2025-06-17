package org.re.hq.driving.domain;

import jakarta.persistence.Converter;
import org.re.common.domain.AbstractPersistenceEnumConverter;

@Converter(autoApply = true)
public class DrivingHistoryStatusConverter extends AbstractPersistenceEnumConverter<DrivingHistoryStatus, String> {
    public DrivingHistoryStatusConverter() {
        super(DrivingHistoryStatus.class);
    }
}
