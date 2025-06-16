package org.re.mdtlog.collector.app.cyclelog;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.re.hq.car.CarFixture;
import org.re.hq.car.service.CarDomainService;
import org.re.hq.company.CompanyFixture;
import org.re.mdtlog.collector.app.cyclelog.dto.MdtAddCycleLogRequestFixture;
import org.re.mdtlog.collector.ignition.MdtLogRequestTimeInfoFixture;
import org.re.mdtlog.domain.MdtTransactionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Import({MdtCycleLogService.class, CarDomainService.class})
@DataJpaTest
class MdtCycleLogServiceTest {
    @Autowired
    MdtCycleLogService mdtCycleLogService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("주기 정보 저장시 차량의 최신 위치를 갱신해야 한다.")
    void saveCycleLog_ShouldUpdateCarLatestLocation() {
        // Given
        var company = em.merge(CompanyFixture.create());
        var car = em.merge(CarFixture.create(company));

        var nCnt = 10;
        var tuid = new MdtTransactionId(UUID.randomUUID().toString());
        var dto = MdtAddCycleLogRequestFixture.create(nCnt);
        var timeInfo = MdtLogRequestTimeInfoFixture.create();

        // When
        var prevLocation = car.getLocation();
        var lastLocation = dto.getLastLocation();
        mdtCycleLogService.addCycleLogs(tuid, dto, timeInfo);

        // Then
        assertNotEquals(prevLocation.getLatitude(), car.getLocation().getLatitude());
        assertNotEquals(prevLocation.getLongitude(), car.getLocation().getLongitude());
        assertEquals(lastLocation.getLatitude(), car.getLocation().getLatitude());
        assertEquals(lastLocation.getLongitude(), car.getLocation().getLongitude());
    }
}
