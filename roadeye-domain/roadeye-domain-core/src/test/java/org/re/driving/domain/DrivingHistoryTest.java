package org.re.driving.domain;

import org.junit.jupiter.api.Test;
import org.re.car.domain.CarLocation;
import org.re.mdtlog.domain.TransactionUUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DrivingHistoryTest {
    private org.re.driving.domain.DrivingSnapShot createSnapShot(int mileage, double lat, double lon, LocalDateTime time) {
        return new org.re.driving.domain.DrivingSnapShot(
            mileage,
            new CarLocation(BigDecimal.valueOf(lat), BigDecimal.valueOf(lon)),
            time
        );
    }

    @Test
    void of_정상_생성() {
        // given
        var txid = TransactionUUID.from(UUID.randomUUID().toString().getBytes());
        org.re.driving.domain.DrivingSnapShot startSnap = createSnapShot(1000, 37.123456, 127.654321, LocalDateTime.now());

        // when
        org.re.driving.domain.DrivingHistory history = org.re.driving.domain.DrivingHistory.of(txid, startSnap);

        // then
        assertThat(history.getStatus()).isEqualTo(org.re.driving.domain.DrivingHistoryStatus.DRIVING);
        assertThat(history.getTxUid()).isEqualTo(txid);
        assertThat(history.getPreviousDrivingSnapShot()).isEqualTo(startSnap);
        assertThat(history.getEndDrivingSnapShot()).isNull();
    }

    @Test
    void end_정상_상태_전이() {
        // given
        var txid = TransactionUUID.from(UUID.randomUUID().toString().getBytes());
        LocalDateTime now = LocalDateTime.now();
        org.re.driving.domain.DrivingSnapShot startSnap = createSnapShot(1000, 37.0, 127.0, now);
        org.re.driving.domain.DrivingSnapShot endSnap = createSnapShot(1050, 37.1, 127.1, now.plusMinutes(30));
        org.re.driving.domain.DrivingHistory history = org.re.driving.domain.DrivingHistory.of(txid, startSnap);

        // when
        history.end(endSnap);

        // then
        assertThat(history.getStatus()).isEqualTo(org.re.driving.domain.DrivingHistoryStatus.ENDED);
        assertThat(history.getEndDrivingSnapShot()).isEqualTo(endSnap);
    }

    @Test
    void end_이미종료된_이력_예외_발생() {
        // given
        var txid = TransactionUUID.from(UUID.randomUUID().toString().getBytes());
        org.re.driving.domain.DrivingSnapShot startSnap = createSnapShot(2000, 37.0, 127.0, LocalDateTime.now());
        org.re.driving.domain.DrivingHistory history = org.re.driving.domain.DrivingHistory.of(txid, startSnap);

        org.re.driving.domain.DrivingSnapShot endSnap = createSnapShot(2100, 37.2, 127.2, LocalDateTime.now().plusHours(1));
        history.end(endSnap);

        // when & then
        assertThatThrownBy(() -> history.end(endSnap))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("status is not DRIVING");
    }
}
