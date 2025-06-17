package org.re.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import org.re.common.exception.MdtLogExceptionCode;

@AllArgsConstructor
public class BaseMdtResponse {
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
