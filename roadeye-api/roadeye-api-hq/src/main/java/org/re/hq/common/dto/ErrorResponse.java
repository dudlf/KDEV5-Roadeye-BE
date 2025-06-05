package org.re.hq.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.re.exception.AppException;
import org.re.exception.AppExceptionCode;
import org.re.hq.domain.exception.DomainException;
import org.re.hq.domain.exception.DomainExceptionCode;

@Getter
@RequiredArgsConstructor
public class ErrorResponse extends BaseResponse {
    private final ErrorData error;

    public record ErrorData(
        String code,
        String message
    ) {

        public static ErrorData of(AppException ae) {
            return of(ae.getCode());
        }

        public static ErrorData of(AppExceptionCode ac) {
            return new ErrorData(ac.getCode(), ac.getMessage());
        }

        public static ErrorData of(DomainException de) {
            return of(de.getExceptionCode());
        }

        public static ErrorData of(DomainExceptionCode dc) {
            return new ErrorData(dc.getCode(), dc.getMessage());
        }
    }
}
