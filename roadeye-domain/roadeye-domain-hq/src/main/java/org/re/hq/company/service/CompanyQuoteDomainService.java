package org.re.hq.company.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.admin.domain.PlatformAdmin;
import org.re.hq.company.domain.Company;
import org.re.hq.company.domain.CompanyQuote;
import org.re.hq.company.domain.CompanyQuoteStatus;
import org.re.hq.company.dto.CompanyQuoteRequestCommand;
import org.re.hq.company.exception.CompanyQuoteDomainException;
import org.re.hq.company.repository.CompanyQuoteRepository;
import org.re.hq.domain.common.DomainService;
import org.re.hq.domain.exception.DomainException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@DomainService
@Transactional
@RequiredArgsConstructor
public class CompanyQuoteDomainService {
    private final CompanyService companyService;
    private final CompanyQuoteRepository companyQuoteRepository;

    public Page<CompanyQuote> findAll(Pageable pageable) {
        return companyQuoteRepository.findAll(pageable);
    }

    public Page<CompanyQuote> findAllByQuoteStatus(CompanyQuoteStatus status, Pageable pageable) {
        return companyQuoteRepository.findAllByQuoteStatus(status, pageable);
    }

    public CompanyQuote findById(Long id) {
        return companyQuoteRepository.findById(id)
            .orElseThrow(() -> new DomainException(CompanyQuoteDomainException.QUOTE_NOT_FOUND));
    }

    public CompanyQuote requestNewQuote(CompanyQuoteRequestCommand command) {
        if (companyService.isBusinessNumberExists(command.businessNumber())) {
            throw new DomainException(CompanyQuoteDomainException.BUSINESS_NUMBER_EXISTS);
        }
        var quoteInfo = command.toQuoteInfo();
        var requestedAt = LocalDateTime.now();
        var quote = new CompanyQuote(quoteInfo, requestedAt);
        return companyQuoteRepository.save(quote);
    }

    public Company approve(PlatformAdmin approver, CompanyQuote quote) {
        quote.approve(approver);
        return companyService.createCompany(quote);
    }

    public CompanyQuote reject(PlatformAdmin approver, CompanyQuote quote) {
        quote.reject(approver);
        return quote;
    }
}
