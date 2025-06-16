package org.re.hq.car.dto;

import org.re.hq.car.domain.Car;
import org.re.hq.car.domain.CarIgnitionStatus;
import org.re.mdtlog.domain.MdtTransactionId;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public record CarDetailsResponse(
    Long id,
    Long companyId,
    String name,
    String licenseNumber,
    String imageUrl,
    BigDecimal latitude,
    BigDecimal longitude,
    Integer mileageInitial,
    Integer mileageCurrent,
    Integer batteryVoltage,
    CarIgnitionStatus ignitionStatus,
    String activeTransactionId,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static CarDetailsResponse from(Car car) {
        return new CarDetailsResponse(
            car.getId(),
            car.getCompany().getId(),
            car.getProfile().getName(),
            car.getProfile().getLicenseNumber(),
            car.getProfile().getImageUrl(),
            car.getLocation().getLatitude(),
            car.getLocation().getLongitude(),
            car.getMileage().getInitial(),
            car.getMileage().getTotal(),
            car.getMdtStatus().getBatteryVoltage(),
            car.getMdtStatus().getIgnition(),
            Optional.ofNullable(car.getMdtStatus().getActiveTuid()).map(MdtTransactionId::toString).orElse(null),
            car.getCreatedAt(),
            car.getUpdatedAt()
        );
    }
}
