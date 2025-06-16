package org.re.hq.driving.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.re.hq.domain.common.PersistenceEnum;

import java.util.Arrays;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DrivingHistoryStatus implements PersistenceEnum<String> {
    // @formatter:off
    DRIVING ("InProgress"),
    ENDED   ("Ended"),
    ;
    // @formatter:on
    private final String code;

    public static DrivingHistoryStatus from(String value) {
        return Arrays.stream(values())
            .filter(it -> it.code.equals(value))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Unknown DrivingHistoryStatus: "));
    }

}
