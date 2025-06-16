package org.re.mdtlog.collector.app.ignition;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.re.hq.car.service.CarDomainService;
import org.re.mdtlog.collector.ignition.MdtIgnitionOffRequestFixture;
import org.re.mdtlog.collector.ignition.MdtIgnitionOnRequestFixture;
import org.re.mdtlog.collector.ignition.MdtLogRequestTimeInfoFixture;
import org.re.mdtlog.domain.MdtLogRepository;
import org.re.mdtlog.domain.MdtTransactionId;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
class MdtIgnitionServiceTest {
    MdtIgnitionService mdtIgnitionService;

    @Mock
    MdtLogRepository mdtLogRepository;

    @Mock
    CarDomainService carDomainService;

    @BeforeEach
    void setUp() {
        mdtIgnitionService = new MdtIgnitionService(carDomainService, mdtLogRepository);
    }

    @AfterEach
    void tearDown() {
        mdtIgnitionService = null;
    }

    @Test
    @DisplayName("시동 ON 이벤트 송신 시 도메인 서비스의 시동 On 메소드를 호출해야 한다.")
    void sendIgnitionOnEvent_ShouldCallTurnOffIgnition() {
        // Given
        var tuid = new MdtTransactionId(UUID.randomUUID().toString());
        var dto = MdtIgnitionOnRequestFixture.create();
        var timeInfo = MdtLogRequestTimeInfoFixture.create();

        // When
        mdtIgnitionService.ignitionOn(tuid, dto, timeInfo);

        // Then
        Mockito.verify(carDomainService, Mockito.times(1))
            .turnOnIgnition(Mockito.any(), Mockito.eq(tuid));
    }

    @Test
    @DisplayName("시동 Off 이벤트 송신 시 도메인 서비스의 시동 Off 메소드를 호출해야 한다.")
    void sendIgnitionOffEvent_ShouldCallTurnOffIgnition() {
        // Given
        var tuid = new MdtTransactionId(UUID.randomUUID().toString());
        var dto = MdtIgnitionOffRequestFixture.create();
        var timeInfo = MdtLogRequestTimeInfoFixture.create();

        // When
        mdtIgnitionService.ignitionOff(tuid, dto, timeInfo);

        // Then
        Mockito.verify(carDomainService, Mockito.times(1))
            .turnOffIgnition(Mockito.any(), Mockito.eq(tuid));
    }
}
