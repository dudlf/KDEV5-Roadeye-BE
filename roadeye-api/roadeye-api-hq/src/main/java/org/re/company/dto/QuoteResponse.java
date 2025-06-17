package org.re.company.dto;

import org.re.company.domain.CompanyQuote;
import org.re.company.domain.CompanyQuoteStatus;

public record QuoteResponse(
    Long id,
    String companyName,
    CompanyQuoteStatus status,
    String rootAccountUsername,
    String companyBusinessNumber,
    String companyEmail
) {
    public static QuoteResponse from(CompanyQuote quote) {
        var info = quote.getQuoteInfo();
        return new QuoteResponse(
            quote.getId(),
            info.getCompanyName(),
            quote.getQuoteStatus(),
            info.getRootAccountUsername(),
            info.getCompanyBusinessNumber(),
            info.getCompanyEmail()
        );
    }
}
