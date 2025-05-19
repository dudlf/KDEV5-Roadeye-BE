package org.re.mdtlog.collector.app.common.dto;

import java.time.LocalDateTime;

public record MdtLogRequestTimeInfo(
    LocalDateTime sentAt,
    LocalDateTime receivedAt
) {
}
