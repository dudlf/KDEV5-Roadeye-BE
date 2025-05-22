package org.re.hq.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.domain.common.BaseEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlatformAdmin extends BaseEntity {
    @Embedded
    PlatformAdminLoginInfo loginInfo;

    @Column(nullable = false)
    Integer loginFailCount;

    @Column(nullable = false)
    String name;

    PlatformAdmin(PlatformAdminLoginInfo loginInfo, Integer loginFailCount, String name) {
        this.loginInfo = loginInfo;
        this.loginFailCount = loginFailCount;
        this.name = name;
    }

    public static PlatformAdmin create(String username, String encodedPassword) {
        var loginInfo = new PlatformAdminLoginInfo(username, encodedPassword);
        return new PlatformAdmin(loginInfo, 0, "admin");
    }
}
