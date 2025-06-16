package org.re.hq.driving.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DrivingHistoryStatus {
    // @formatter:off
    DRIVING ("InProgress"),
    ENDED   ("Ended"),
    ;
    // @formatter:on
    private final String value;

    public static DrivingHistoryStatus from(String value) {
        return Arrays.stream(values())
            .filter(it -> it.value.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown DrivingHistoryStatus: "));
    }

}
