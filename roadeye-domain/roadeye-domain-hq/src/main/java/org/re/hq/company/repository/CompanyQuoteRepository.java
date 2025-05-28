package org.re.hq.company.repository;

import org.re.hq.company.domain.CompanyQuoteRequest;
import org.re.hq.company.domain.CompanyQuoteStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyQuoteRepository extends JpaRepository<CompanyQuoteRequest, Long> {
    Page<CompanyQuoteRequest> findAllByQuoteStatus(CompanyQuoteStatus quoteStatus, Pageable pageable);
}
