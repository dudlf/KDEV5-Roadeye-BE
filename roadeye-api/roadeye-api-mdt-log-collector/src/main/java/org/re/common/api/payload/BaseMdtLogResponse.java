package org.re.common.api.payload;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.re.common.exception.MdtLogExceptionCode;

public class BaseMdtLogResponse extends BaseMdtResponse {
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
