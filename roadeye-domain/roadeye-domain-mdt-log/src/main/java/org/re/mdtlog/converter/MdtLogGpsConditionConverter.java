package org.re.mdtlog.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.re.mdtlog.domain.MdtLogGpsCondition;

@Converter
public class MdtLogGpsConditionConverter implements AttributeConverter<MdtLogGpsCondition, String> {
    @Override
    public String convertToDatabaseColumn(MdtLogGpsCondition gcd) {
        return gcd.getCode();
    }

    @Override
    public MdtLogGpsCondition convertToEntityAttribute(String s) {
        if (s == null) {
            return null;
        }
        return MdtLogGpsCondition.of(s);
    }
}
