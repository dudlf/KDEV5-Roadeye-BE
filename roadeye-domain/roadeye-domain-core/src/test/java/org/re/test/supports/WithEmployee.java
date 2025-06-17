package org.re.test.supports;

import org.junit.jupiter.api.extension.ExtendWith;
import org.re.test.api.extension.EmployeeParameterResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(EmployeeParameterResolver.class)
public @interface WithEmployee {
}
