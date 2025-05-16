package org.re.mdtlog.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.re.mdtlog.domain.MdtLogEventType;

@Converter
public class MdtLogEventTypeConverter implements AttributeConverter<MdtLogEventType, String> {

    @Override
    public String convertToDatabaseColumn(MdtLogEventType attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.getCode();
    }

    @Override
    public MdtLogEventType convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return MdtLogEventType.fromCode(dbData);
    }
}
