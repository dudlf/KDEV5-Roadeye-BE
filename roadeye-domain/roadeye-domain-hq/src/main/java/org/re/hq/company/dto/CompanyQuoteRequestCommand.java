package org.re.hq.company.dto;

import lombok.With;
import org.re.hq.company.domain.CompanyQuoteInfo;

public record CompanyQuoteRequestCommand(
    String name,
    String username,
    @With String password,
    String email,
    String businessNumber
) {
    public CompanyQuoteInfo toQuoteInfo() {
        return new CompanyQuoteInfo(
            name,
            username,
            password,
            email,
            businessNumber
        );
    }
}
