package org.re.hq.company.repository;

import org.re.hq.company.domain.CompanyQuoteRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyQuoteRepository extends JpaRepository<CompanyQuoteRequest, Long> {

}
