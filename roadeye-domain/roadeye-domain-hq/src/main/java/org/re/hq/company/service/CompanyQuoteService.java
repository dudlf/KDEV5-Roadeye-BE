package org.re.hq.company.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.admin.domain.PlatformAdmin;
import org.re.hq.company.domain.CompanyQuoteRequest;
import org.re.hq.company.domain.CompanyQuoteStatus;
import org.re.hq.company.dto.CompanyQuoteRequestCommand;
import org.re.hq.company.repository.CompanyQuoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyQuoteService {
    private final CompanyService companyService;
    private final CompanyQuoteRepository companyQuoteRepository;

    public Page<CompanyQuoteRequest> findAll(Pageable pageable) {
        return companyQuoteRepository.findAll(pageable);
    }

    public Page<CompanyQuoteRequest> findAllByQuoteStatus(CompanyQuoteStatus status, Pageable pageable) {
        return companyQuoteRepository.findAllByQuoteStatus(status, pageable);
    }

    public CompanyQuoteRequest findById(Long id) {
        return companyQuoteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Quote request not found with id: " + id));
    }

    public CompanyQuoteRequest requestNewQuote(CompanyQuoteRequestCommand command) {
        if (companyService.isBusinessNumberExists(command.businessNumber())) {
            throw new IllegalArgumentException("Business number already exists: " + command.businessNumber());
        }
        var quoteInfo = command.toQuoteInfo();
        var requestedAt = LocalDateTime.now();
        var quoteRequest = new CompanyQuoteRequest(quoteInfo, requestedAt);
        return companyQuoteRepository.save(quoteRequest);
    }

    public CompanyQuoteRequest approve(PlatformAdmin approver, CompanyQuoteRequest quoteRequest) {
        quoteRequest.approve(approver);
        return quoteRequest;
    }

    public CompanyQuoteRequest reject(PlatformAdmin approver, CompanyQuoteRequest quoteRequest) {
        quoteRequest.reject(approver);
        return quoteRequest;
    }
}
