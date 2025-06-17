package org.re.company.api;

import lombok.RequiredArgsConstructor;
import org.re.common.dto.SingleItemResponse;
import org.re.company.dto.CompanyQuoteCreationRequest;
import org.re.company.dto.QuoteResponse;
import org.re.company.service.CompanyQuoteService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company/quotes")
@RequiredArgsConstructor
public class CompanyQuoteApi {
    private final CompanyQuoteService quoteService;

    @PostMapping
    public SingleItemResponse<QuoteResponse> createQuote(
        @RequestBody CompanyQuoteCreationRequest request
    ) {
        var quote = quoteService.createQuote(request);
        return SingleItemResponse.of(quote, QuoteResponse::from);
    }
}
