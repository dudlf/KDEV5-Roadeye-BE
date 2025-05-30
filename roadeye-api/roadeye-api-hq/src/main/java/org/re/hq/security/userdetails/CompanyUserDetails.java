package org.re.hq.security.userdetails;

import lombok.Getter;
import org.jspecify.annotations.NonNull;
import org.re.hq.employee.domain.Employee;
import org.re.hq.security.domain.AuthMemberType;
import org.re.hq.tenant.TenantId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

@Getter
public class CompanyUserDetails implements UserDetails {
    private static final Collection<? extends GrantedAuthority> DEFAULT_AUTHORITIES
        = Collections.unmodifiableCollection(AuthorityUtils.createAuthorityList(AuthMemberType.USER.getValue()));

    @NonNull
    private final TenantId tenantId;
    private final String username;
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean isAccountNonExpired;
    private final boolean isAccountNonLocked;
    private final boolean isCredentialsNonExpired;
    private final boolean isEnabled;

    private CompanyUserDetails(
        @NonNull TenantId tenantId,
        String username,
        String password,
        Collection<? extends GrantedAuthority> authorities,
        boolean isAccountNonExpired,
        boolean isAccountNonLocked,
        boolean isCredentialsNonExpired,
        boolean isEnabled
    ) {
        this.tenantId = tenantId;
        this.username = username;
        this.password = password;
        this.authorities = Collections.unmodifiableCollection(authorities);
        this.isAccountNonExpired = isAccountNonExpired;
        this.isAccountNonLocked = isAccountNonLocked;
        this.isCredentialsNonExpired = isCredentialsNonExpired;
        this.isEnabled = isEnabled;
    }

    public static UserDetails from(Employee user) {
        var tenantId = new TenantId(user.getTenantId());
        return new CompanyUserDetails(
            tenantId,
            user.getCredentials().loginId(),
            user.getCredentials().password(),
            createAuthorities(user),
            true,
            true,
            true,
            true
        );
    }

    private static Collection<? extends GrantedAuthority> createAuthorities(Employee user) {
        return Stream.concat(
            DEFAULT_AUTHORITIES.stream(),
            Stream.of(user.getMetadata().getRole())
                .map((r) -> new SimpleGrantedAuthority(r.name()))
        ).toList();
    }
}
