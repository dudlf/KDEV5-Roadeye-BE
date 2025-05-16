package org.re.mdtlog.domain;

import java.nio.ByteBuffer;
import java.util.UUID;

public class MdtTransactionId {
    private final UUID tuid;

    public MdtTransactionId(String raw) {
        this.tuid = UUID.fromString(raw);
    }

    private static byte[] uuidToBytes(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();
    }

    public static MdtTransactionId fromBytes(byte[] dbData) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(dbData);
        long mostSigBits = byteBuffer.getLong();
        long leastSigBits = byteBuffer.getLong();
        UUID uuid = new UUID(mostSigBits, leastSigBits);
        return new MdtTransactionId(uuid.toString());
    }

    public String toString() {
        return tuid.toString();
    }

    public byte[] toBytes() {
        return uuidToBytes(tuid);
    }
}
