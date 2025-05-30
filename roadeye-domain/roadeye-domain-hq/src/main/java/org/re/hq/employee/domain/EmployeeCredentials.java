package org.re.hq.employee.domain;

import jakarta.persistence.Embeddable;
import org.re.hq.company.domain.CompanyQuoteRequest;

@Embeddable
public record EmployeeCredentials(
    String loginId,
    String password
) {
    public static EmployeeCredentials from(CompanyQuoteRequest quoteRequest) {
        return new EmployeeCredentials(
            quoteRequest.getQuoteInfo().getRootAccountUsername(),
            quoteRequest.getQuoteInfo().getRootAccountPassword()
        );
    }
}
