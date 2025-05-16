package org.re.mdtlog.collector.app.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.re.mdtlog.collector.exception.MdtLogExceptionCode;

@AllArgsConstructor
public class BaseResponse {
    private final MdtLogExceptionCode code;

    @JsonProperty("rstCd")
    public String getCode() {
        return code.getCode();
    }

    @JsonProperty("rstMsg")
    public String getMessage() {
        return code.getMessage();
    }
}
