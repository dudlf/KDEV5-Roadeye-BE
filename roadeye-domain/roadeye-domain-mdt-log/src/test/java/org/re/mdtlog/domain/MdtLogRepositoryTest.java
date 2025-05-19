package org.re.mdtlog.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MdtLogRepositoryTest {

    @Autowired
    private MdtLogRepository mdtLogRepository;

    @Test
    void uuid_값이_정상적으로_저장됩니다() {
        var now = LocalDateTime.now();
        MdtLog mdtLog = MdtLog.builder()
            .packetVer(1)
            .eventType(MdtLogEventType.CycleLog)
            .txUid(new byte[16])
            .carId("TEST_CAR")
            .terminalId("TERM123")
            .manufactureId("MANUF123")
            .deviceId("DEV123")
            .gpsCond(MdtLogGpsCondition.Normal)
            .gpsLat(new BigDecimal("37.123456"))
            .gpsLon(new BigDecimal("127.123456"))
            .mdtAngle(90)
            .mdtSpeed(60)
            .mdtMileageSum(1000)
            .mdtBatteryVoltage(12)
            .mdtIgnitionOnTime(now)
            .mdtIgnitionOffTime(now.plusHours(1))
            .occurredAt(now)
            .sentAt(now)
            .receivedAt(now)
            .build();

        var savedMdtLog = mdtLogRepository.save(mdtLog);

        assertThat(savedMdtLog.getLogId()).isNotNull();
        assertThat(savedMdtLog).isEqualTo(mdtLog);
    }
}
