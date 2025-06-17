package org.re.mdtlog.collector.ignition;

import org.re.mdtlog.collector.app.common.dto.MdtLogRequestTimeInfo;

import java.time.LocalDateTime;

public class MdtLogRequestTimeInfoFixture {
    public static MdtLogRequestTimeInfo create() {
        var sendAt = LocalDateTime.now();
        var receivedAt = sendAt.plusSeconds(5);
        return new MdtLogRequestTimeInfo(sendAt, receivedAt);
    }
}
