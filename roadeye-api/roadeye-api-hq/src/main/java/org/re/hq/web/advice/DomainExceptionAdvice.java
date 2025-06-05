package org.re.hq.web.advice;

import org.re.hq.common.dto.ErrorResponse;
import org.re.hq.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DomainExceptionAdvice {
    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDomainException(DomainException e) {
        return new ErrorResponse(ErrorResponse.ErrorData.of(e));
    }
}
