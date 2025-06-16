package org.re.hq.driving.domain;

import org.junit.jupiter.api.Test;
import org.re.hq.car.domain.CarLocation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DrivingHistoryTest {
    private DrivingSnapShot createSnapShot(int mileage, double lat, double lon, LocalDateTime time) {
        return new DrivingSnapShot(
            mileage,
            new CarLocation(BigDecimal.valueOf(lat), BigDecimal.valueOf(lon)),
            time
        );
    }

    @Test
    void of_정상_생성() {
        // given
        String txid = "TX001";
        DrivingSnapShot startSnap = createSnapShot(1000, 37.123456, 127.654321, LocalDateTime.now());

        // when
        DrivingHistory history = DrivingHistory.of(txid, startSnap);

        // then
        assertThat(history.getStatus()).isEqualTo(DrivingHistoryStatus.DRIVING);
        assertThat(history.getTxid()).isEqualTo(txid);
        assertThat(history.getPreviousDrivingSnapShot()).isEqualTo(startSnap);
        assertThat(history.getEndDrivingSnapShot()).isNull();
    }

    @Test
    void end_정상_상태_전이() {
        // given
        String txid = "TX002";
        LocalDateTime now = LocalDateTime.now();
        DrivingSnapShot startSnap = createSnapShot(1000, 37.0, 127.0, now);
        DrivingSnapShot endSnap = createSnapShot(1050, 37.1, 127.1, now.plusMinutes(30));
        DrivingHistory history = DrivingHistory.of(txid, startSnap);

        // when
        history.end(endSnap);

        // then
        assertThat(history.getStatus()).isEqualTo(DrivingHistoryStatus.ENDED);
        assertThat(history.getEndDrivingSnapShot()).isEqualTo(endSnap);
    }

    @Test
    void end_이미종료된_이력_예외_발생() {
        // given
        String txid = "TX003";
        DrivingSnapShot startSnap = createSnapShot(2000, 37.0, 127.0, LocalDateTime.now());
        DrivingHistory history = DrivingHistory.of(txid, startSnap);

        DrivingSnapShot endSnap = createSnapShot(2100, 37.2, 127.2, LocalDateTime.now().plusHours(1));
        history.end(endSnap);

        // when & then
        assertThatThrownBy(() -> history.end(endSnap))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("status is not DRIVING");
    }
}
