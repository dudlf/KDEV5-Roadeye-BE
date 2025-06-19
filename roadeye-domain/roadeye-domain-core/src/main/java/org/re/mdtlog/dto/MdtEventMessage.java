package org.re.mdtlog.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record MdtEventMessage<D>(
    @Nullable
    String transactionId,
    D payload,
    @NotNull
    LocalDateTime sentAt,
    @NotNull
    LocalDateTime receivedAt
) {
}
