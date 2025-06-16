package org.re.mdtlog.collector.ignition;

import org.re.mdtlog.collector.app.ignition.dto.MdtIgnitionOnRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MdtIgnitionOnRequestFixture {
    public static MdtIgnitionOnRequest create() {
        var carId = 1L;
        var terminalId = "TID123456";
        var manufacturerId = "MID987654";
        var packetVersion = 1;
        var deviceId = "DID0001";
        var ignitionOnTime = LocalDateTime.of(2024, 6, 1, 12, 0, 0);
        var gpsCondition = org.re.mdtlog.domain.MdtLogGpsCondition.NORMAL;
        var gpsLatitude = new BigDecimal("37.5665");
        var gpsLongitude = new BigDecimal("126.9780");
        var mdtAngle = 90;
        var mdtSpeed = 60;
        var mdtMileageSum = 123456;
        return new MdtIgnitionOnRequest(
            carId,
            terminalId,
            manufacturerId,
            packetVersion,
            deviceId,
            ignitionOnTime,
            gpsCondition,
            gpsLatitude,
            gpsLongitude,
            mdtAngle,
            mdtSpeed,
            mdtMileageSum
        );
    }
}
