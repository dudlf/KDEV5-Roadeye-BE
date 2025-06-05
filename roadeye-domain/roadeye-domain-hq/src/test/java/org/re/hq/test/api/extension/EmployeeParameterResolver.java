package org.re.hq.test.api.extension;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.re.hq.company.domain.Company;
import org.re.hq.employee.EmployeeFixture;
import org.re.hq.employee.domain.Employee;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class EmployeeParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        var paramType = parameterContext.getParameter().getType();
        var paramName = parameterContext.getParameter().getName();
        return Employee.class.isAssignableFrom(paramType)
            && (paramName.equals("normalEmployee") || paramName.equals("rootEmployee"));
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        var springContext = SpringExtension.getApplicationContext(extensionContext);
        var em = springContext.getBean(EntityManager.class);

        Company company = extensionContext.getStore(ExtensionContext.Namespace.GLOBAL)
            .get("company", Company.class);

        String name = parameterContext.getParameter().getName();
        Employee employee = name.equals("rootEmployee")
            ? EmployeeFixture.createRoot(company)
            : EmployeeFixture.createNormal(company);

        em.persist(employee);
        em.flush();
        return employee;
    }
}
