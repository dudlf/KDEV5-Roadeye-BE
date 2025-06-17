package org.re.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class SuccessResponse<T> extends org.re.common.dto.BaseResponse {
    private final T data;
}
