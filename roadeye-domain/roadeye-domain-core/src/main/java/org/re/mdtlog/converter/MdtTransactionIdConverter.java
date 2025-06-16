package org.re.mdtlog.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.re.mdtlog.domain.MdtTransactionId;

@Converter
public class MdtTransactionIdConverter implements AttributeConverter<MdtTransactionId, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(MdtTransactionId attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toBytes();
    }

    @Override
    public MdtTransactionId convertToEntityAttribute(byte[] dbData) {
        if (dbData == null) {
            return null;
        }
        return MdtTransactionId.from(dbData);
    }
}
