package org.re.mdtlog.collector.app.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.re.mdtlog.collector.exception.MdtLogExceptionCode;

public class BaseMdtLogResponse extends BaseResponse {
    private final Long carId;

    public BaseMdtLogResponse(Long carId) {
        super(MdtLogExceptionCode.Success);
        this.carId = carId;
    }

    @JsonProperty("mdn")
    public Long getCarId() {
        return carId;
    }
}
