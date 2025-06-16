package org.re.mdtlog.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.re.mdtlog.domain.TransactionUUID;

@Converter
public class TransactionIdConverter implements AttributeConverter<TransactionUUID, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(TransactionUUID attribute) {
        return attribute.toBytes();
    }

    @Override
    public TransactionUUID convertToEntityAttribute(byte[] dbData) {
        return TransactionUUID.from(dbData);
    }
}
