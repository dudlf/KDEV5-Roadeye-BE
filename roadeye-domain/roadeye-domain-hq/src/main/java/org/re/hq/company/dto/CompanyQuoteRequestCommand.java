package org.re.hq.company.dto;

import org.re.hq.company.domain.CompanyQuoteRequestInfo;

public record CompanyQuoteRequestCommand(
    String name,
    String username,
    String password,
    String email,
    String businessNumber
) {
    public CompanyQuoteRequestInfo toQuoteInfo() {
        return new CompanyQuoteRequestInfo(
            name,
            username,
            password,
            email,
            businessNumber
        );
    }
}
