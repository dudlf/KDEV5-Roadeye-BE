package org.re.mdtlog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import org.re.mdtlog.databind.MdtLogGpsConditionDeserializer;
import org.re.mdtlog.domain.MdtLogGpsCondition;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder(access = AccessLevel.PACKAGE)
public record MdtCycleLogMessage(
    @JsonProperty("mdn")
    @NotNull
    Long carId,

    @JsonProperty("tid")
    @NotNull
    String terminalId,

    @JsonProperty("mid")
    @NotNull
    String manufacturerId,

    @JsonProperty("pv")
    @Min(0)
    @Max(65535)
    int packetVersion,

    @JsonProperty("did")
    @NotNull
    String deviceId,

    @JsonProperty("oTime")
    @JsonFormat(pattern = "yyyyMMddHHmm")
    @NotNull
    LocalDateTime occurredAt,

    @JsonProperty("cCnt")
    int cycleCount,

    @JsonProperty("cList")
    @NotNull
    List<MdtCycleLogItem> cycleLogList
) {
    public MdtCycleLogMessage {
        if (cycleCount != cycleLogList.size()) {
            throw new IllegalArgumentException("Cycle count mismatch");
        }
    }

    @Builder(access = AccessLevel.PACKAGE)
    public record MdtCycleLogItem(
        int sec,

        @JsonProperty("gcd")
        @JsonDeserialize(using = MdtLogGpsConditionDeserializer.class)
        @NotNull
        MdtLogGpsCondition gpsCondition,

        @JsonProperty("lat")
        @DecimalMin(value = "-90.0")
        @DecimalMax(value = "90.0")
        @NotNull
        BigDecimal gpsLatitude,

        @JsonProperty("lon")
        @DecimalMin(value = "-180.0")
        @DecimalMax(value = "180.0")
        @NotNull
        BigDecimal gpsLongitude,

        @JsonProperty("ang")
        @Min(0)
        @Max(365)
        int mdtAngle,

        @JsonProperty("spd")
        @Min(0)
        @Max(255)
        int mdtSpeed,

        @JsonProperty("sum")
        @Min(0)
        @Max(9999999)
        int mdtMileageSum,

        @JsonProperty("bat")
        @Min(0)
        @Max(9999)
        int batteryVoltage
    ) {
    }
}
