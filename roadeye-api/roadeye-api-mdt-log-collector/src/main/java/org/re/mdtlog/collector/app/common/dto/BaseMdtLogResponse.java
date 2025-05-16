package org.re.mdtlog.collector.app.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.re.mdtlog.collector.exception.MdtLogExceptionCode;

public class BaseMdtLogResponse extends BaseResponse {
    private final String carId;

    public BaseMdtLogResponse(String carId) {
        super(MdtLogExceptionCode.Success);
        this.carId = carId;
    }

    @JsonProperty("mdn")
    public String getCarId() {
        return carId;
    }
}
