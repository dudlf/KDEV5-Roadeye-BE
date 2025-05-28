package org.re.hq.company.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.company.domain.CompanyQuoteRequest;
import org.re.hq.company.dto.CompanyQuoteRequestCommand;
import org.re.hq.company.repository.CompanyQuoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyQuoteService {
    private final CompanyService companyService;
    private final CompanyQuoteRepository companyQuoteRepository;

    public CompanyQuoteRequest requestNewQuote(CompanyQuoteRequestCommand command) {
        if (companyService.isBusinessNumberExists(command.businessNumber())) {
            throw new IllegalArgumentException("Business number already exists: " + command.businessNumber());
        }
        var quoteInfo = command.toQuoteInfo();
        var requestedAt = LocalDateTime.now();
        var quoteRequest = new CompanyQuoteRequest(quoteInfo, requestedAt);
        return companyQuoteRepository.save(quoteRequest);
    }
}
