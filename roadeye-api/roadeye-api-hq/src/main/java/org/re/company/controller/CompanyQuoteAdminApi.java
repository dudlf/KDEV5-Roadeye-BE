package org.re.company.controller;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.re.company.dto.QuoteResponse;
import org.re.company.service.CompanyQuoteService;
import org.re.hq.common.dto.PageResponse;
import org.re.hq.security.userdetails.PlatformAdminUserDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/company/quotes")
@RequiredArgsConstructor
public class CompanyQuoteAdminApi {
    private final CompanyQuoteService quoteService;

    @GetMapping
    public PageResponse<QuoteResponse> findAll(Pageable pageable) {
        var page = quoteService.findAll(pageable);
        return PageResponse.of(page, QuoteResponse::from);
    }

    @GetMapping("/{quoteId}")
    public QuoteResponse findById(@PathVariable Long quoteId) {
        var quote = quoteService.findById(quoteId);
        return QuoteResponse.from(quote);
    }

    @PostMapping("/{quoteId}/approve")
    public QuoteResponse approveQuote(
        @NonNull PlatformAdminUserDetails userDetails,
        @PathVariable Long quoteId
    ) {
        var quote = quoteService.approveQuote(userDetails, quoteId);
        return QuoteResponse.from(quote);
    }

    @PostMapping("/{quoteId}/reject")
    public QuoteResponse rejectQuote(
        @NonNull PlatformAdminUserDetails userDetails,
        @PathVariable Long quoteId
    ) {
        var quote = quoteService.rejectQuote(userDetails, quoteId);
        return QuoteResponse.from(quote);
    }
}
