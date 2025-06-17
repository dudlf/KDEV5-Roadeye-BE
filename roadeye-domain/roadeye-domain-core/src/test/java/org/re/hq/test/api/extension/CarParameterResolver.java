package org.re.hq.test.api.extension;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.re.car.CarFixture;
import org.re.car.domain.Car;
import org.re.hq.company.domain.Company;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class CarParameterResolver implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return Car.class.isAssignableFrom(parameterContext.getParameter().getType());
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        var springContext = SpringExtension.getApplicationContext(extensionContext);
        var em = springContext.getBean(EntityManager.class);

        Company company = extensionContext.getStore(ExtensionContext.Namespace.GLOBAL)
            .get("company", Company.class);

        var car = CarFixture.create(company);
        em.persist(car);
        em.flush();

        return car;
    }
}
