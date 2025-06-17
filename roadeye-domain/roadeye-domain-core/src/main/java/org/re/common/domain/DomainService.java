package org.re.common.domain;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Service
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DomainService {
}
