package org.re.mdtlog.collector.app.ignition.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.re.mdtlog.collector.app.databind.MdtLogGpsConditionDeserializer;
import org.re.mdtlog.domain.MdtLog;
import org.re.mdtlog.domain.MdtLogGpsCondition;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MdtIgnitionOnRequest(
    @JsonProperty("mdn")
    String carId,

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
    int mdtMileageSum
) {
    public MdtLog toMdtLog() {
        return MdtLog.builder()
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
            .build();
    }
}
