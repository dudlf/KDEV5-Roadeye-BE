package org.re.mdtlog.collector.exception;

import lombok.Getter;
import org.re.exception.AppExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
public enum MdtLogExceptionCode implements AppExceptionCode {
    // @formatter:off
    Success("000", "Success"),
    ;
    // @formatter:on

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    MdtLogExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
        this.httpStatus = HttpStatus.OK;
    }
}
