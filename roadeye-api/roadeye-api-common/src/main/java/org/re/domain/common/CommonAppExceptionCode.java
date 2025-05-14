package org.re.domain.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.re.exception.AppExceptionCode;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonAppExceptionCode implements AppExceptionCode {
    // @formatter:off
    INTERNAL_SERVER_ERROR("500", "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR),
    ;
    // @formatter:on

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;
}
