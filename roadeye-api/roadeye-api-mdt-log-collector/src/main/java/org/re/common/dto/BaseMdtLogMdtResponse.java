package org.re.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.re.common.exception.MdtLogExceptionCode;

public class BaseMdtLogMdtResponse extends BaseMdtResponse {
    private final String carId;

    public BaseMdtLogMdtResponse(String carId) {
        super(MdtLogExceptionCode.Success);
        this.carId = carId;
    }

    @JsonProperty("mdn")
    public String getCarId() {
        return carId;
    }
}
