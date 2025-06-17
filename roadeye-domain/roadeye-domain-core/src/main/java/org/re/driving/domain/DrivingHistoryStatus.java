package org.re.driving.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.re.common.domain.PersistenceEnum;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum DrivingHistoryStatus implements PersistenceEnum<String> {
    // @formatter:off
    DRIVING ("InProgress"),
    ENDED   ("Ended"),
    ;
    // @formatter:on
    private final String code;

}
