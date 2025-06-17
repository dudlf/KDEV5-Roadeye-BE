package org.re.hq.car.dto;

import org.re.hq.common.dto.SuccessResponse;

public class CarStatusResponse extends SuccessResponse<CarStatusResponse.CarStatusInfo> {
    public CarStatusResponse(CarStatusInfo data) {
        super(data);
    }

    public static CarStatusResponse from(CarsStatusResult status) {
        var info = new CarStatusInfo(
            status.drivings(),
            status.idles(),
            status.drivings() + status.idles()
        );
        return new CarStatusResponse(info);
    }

    public record CarStatusInfo(
        long drivings,
        long idles,
        long total
    ) {
    }
}
