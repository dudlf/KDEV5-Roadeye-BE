package org.re.hq.company.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.admin.service.PlatformAdminService;
import org.re.hq.company.domain.CompanyQuote;
import org.re.hq.company.dto.CompanyQuoteCreationRequest;
import org.re.hq.security.userdetails.PlatformAdminUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyQuoteService {
    private final CompanyQuoteDomainService quoteDomainService;
    private final PlatformAdminService platformAdminService;

    public Page<CompanyQuote> findAll(Pageable pageable) {
        return quoteDomainService.findAll(pageable);
    }

    public CompanyQuote createQuote(CompanyQuoteCreationRequest request) {
        var command = request.toCommand();
        return quoteDomainService.requestNewQuote(command);
    }

    public CompanyQuote findById(Long quoteId) {
        return quoteDomainService.findById(quoteId);
    }

    public CompanyQuote approveQuote(PlatformAdminUserDetails userDetails, Long quoteId) {
        var quote = quoteDomainService.findById(quoteId);
        var approver = platformAdminService.findById(userDetails.getId());
        quoteDomainService.approve(approver, quote);
        return quote;
    }
}
