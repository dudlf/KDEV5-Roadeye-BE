package org.re.mdtlog.domain;

import jakarta.persistence.Column;
import lombok.EqualsAndHashCode;

import java.nio.ByteBuffer;
import java.util.UUID;

@EqualsAndHashCode
public class TransactionUUID {

    @Column(name = "tx_uid", columnDefinition = "BINARY(16)", nullable = false)
    private final UUID uuid;

    public TransactionUUID(String raw) {
        this.uuid = UUID.fromString(raw);
    }

    private static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    public static TransactionUUID from(byte[] dbData) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(dbData);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        return new TransactionUUID(uuid.toString());
    }

    public String toString() {
        return uuid.toString();
    }

    public byte[] toBytes() {
        return uuidToBytes(uuid);
    }


}
