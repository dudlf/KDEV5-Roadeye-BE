package org.re.hq.test.security;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
@WithSecurityContext(factory = MockPlatformAdminUserDetailsSecurityContextFactory.class)
public @interface MockPlatformAdminUserDetails {
    long id() default 1L;

    String username() default "username";

    String password() default "{noop}password";
}
