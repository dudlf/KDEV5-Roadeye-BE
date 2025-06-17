package org.re.mdt.dto;

import java.math.BigDecimal;

public class MdtCycleLogItemFixture {
    public static MdtAddCycleLogRequest.MdtCycleLogItem create(int sec) {
        return MdtAddCycleLogRequest.MdtCycleLogItem.builder()
            .sec(sec)
            .gpsLatitude(BigDecimal.valueOf(37.123456))
            .gpsLongitude(BigDecimal.valueOf(127.123456))
            .mdtAngle(100)
            .mdtSpeed(100)
            .mdtMileageSum(1000)
            .batteryVoltage(100)
            .build();
    }
}
