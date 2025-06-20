package org.re.mdtlog.databind;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.re.mdtlog.domain.TransactionUUID;

import java.io.IOException;

public class TransactionUUIDJacksonSerializer extends StdSerializer<TransactionUUID> {
    public TransactionUUIDJacksonSerializer() {
        super(TransactionUUID.class);
    }

    @Override
    public void serialize(TransactionUUID tuid, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (tuid == null) {
            gen.writeNull();
        } else {
            gen.writeString(tuid.value().toString());
        }
    }
}
