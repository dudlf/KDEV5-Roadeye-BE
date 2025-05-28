package org.re.hq.company;

import org.re.hq.company.domain.CompanyQuoteRequest;

import java.time.LocalDateTime;

public class CompanyQuoteRequestFixture {
    public static CompanyQuoteRequest create() {
        var companyQuoteRequestInfo = CompanyQuoteRequestInfoFixture.create();
        var requestedAt = LocalDateTime.now();
        return new CompanyQuoteRequest(companyQuoteRequestInfo, requestedAt);
    }
}
