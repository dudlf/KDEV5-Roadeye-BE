package org.re.mdt.dto;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

public class MdtAddCycleLogRequestFixture {
    public static MdtAddCycleLogRequest create(int cCnt) {
        var occurredAt = LocalDateTime.now();
        var cItems = IntStream.range(1, cCnt + 1)
            .mapToObj(MdtCycleLogItemFixture::create)
            .toList();

        return MdtAddCycleLogRequest.builder()
            .carId("car001")
            .terminalId("term001")
            .manufacturerId("man001")
            .packetVersion(1)
            .deviceId("dev001")
            .cycleCount(cCnt)
            .cycleLogList(cItems)
            .occurredAt(occurredAt)
            .build();
    }
}
