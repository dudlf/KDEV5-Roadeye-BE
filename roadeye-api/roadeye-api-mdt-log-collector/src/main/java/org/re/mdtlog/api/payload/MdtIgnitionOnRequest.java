package org.re.mdtlog.api.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.*;
import org.re.common.api.payload.MdtLogRequestTimeInfo;
import org.re.mdtlog.domain.MdtLog;
import org.re.mdtlog.domain.MdtLogEventType;
import org.re.mdtlog.domain.MdtLogGpsCondition;
import org.re.mdtlog.domain.TransactionUUID;
import org.re.web.databind.MdtLogGpsConditionDeserializer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MdtIgnitionOnRequest(
    @JsonProperty("mdn")
    Long carId,

    @JsonProperty("tid")
    String terminalId,

    @JsonProperty("mid")
    String manufacturerId,

    @JsonProperty("pv")
    @Min(0)
    @Max(65535)
    int packetVersion,

    @JsonProperty("did")
    String deviceId,

    @JsonProperty("onTime")
    @JsonFormat(pattern = "yyyyMMddHHmmss")
    LocalDateTime ignitionOnTime,

    @NotNull
    @JsonProperty("gcd")
    @JsonDeserialize(using = MdtLogGpsConditionDeserializer.class)
    MdtLogGpsCondition gpsCondition,

    @JsonProperty("lat")
    @DecimalMin(value = "-90.0")
    @DecimalMax(value = "90.0")
    BigDecimal gpsLatitude,

    @JsonProperty("lon")
    @DecimalMin(value = "-180.0")
    @DecimalMax(value = "180.0")
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
    int mdtMileageSum
) {
    public MdtLog toMdtLog(TransactionUUID tuid, MdtLogRequestTimeInfo tInfo) {
        return MdtLog.builder()
            .eventType(MdtLogEventType.IGNITION)
            .txUid(tuid)
            .carId(carId)
            .terminalId(terminalId)
            .manufactureId(manufacturerId)
            .packetVer(packetVersion)
            .deviceId(deviceId)
            .mdtIgnitionOnTime(ignitionOnTime)
            .gpsCond(gpsCondition)
            .gpsLat(gpsLatitude)
            .gpsLon(gpsLongitude)
            .mdtAngle(mdtAngle)
            .mdtSpeed(mdtSpeed)
            .mdtMileageSum(mdtMileageSum)
            .occurredAt(ignitionOnTime)
            .sentAt(tInfo.sentAt())
            .receivedAt(tInfo.receivedAt())
            .build();
    }
}
