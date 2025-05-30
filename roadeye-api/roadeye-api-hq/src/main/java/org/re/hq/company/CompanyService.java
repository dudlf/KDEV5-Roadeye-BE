package org.re.hq.company;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.company.domain.Company;
import org.re.hq.company.service.CompanyDomainService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyDomainService companyDomainService;

    public Page<Company> findAll(Pageable pageable) {
        return companyDomainService.findAll(pageable);
    }

    public Company findById(Long id) {
        return companyDomainService.findById(id);
    }
}
