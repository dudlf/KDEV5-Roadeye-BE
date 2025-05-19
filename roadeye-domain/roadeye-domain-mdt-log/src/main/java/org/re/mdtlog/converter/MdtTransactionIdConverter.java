package org.re.mdtlog.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.re.mdtlog.domain.MdtTransactionId;

@Converter
public class MdtTransactionIdConverter implements AttributeConverter<MdtTransactionId, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(MdtTransactionId attribute) {
        return attribute.toBytes();
    }

    @Override
    public MdtTransactionId convertToEntityAttribute(byte[] dbData) {
        return MdtTransactionId.from(dbData);
    }
}
