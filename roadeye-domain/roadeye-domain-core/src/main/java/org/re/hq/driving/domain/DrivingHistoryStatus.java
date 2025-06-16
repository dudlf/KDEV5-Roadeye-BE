package org.re.hq.driving.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DrivingHistoryStatus {
    // @formatter:off
    DRIVING ("InProgress"),
    ENDED   ("Ended"),
    ;
    // @formatter:on
    private final String value;
}
