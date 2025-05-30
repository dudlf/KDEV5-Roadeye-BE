package org.re.hq.company.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.re.hq.company.domain.Company;
import org.re.hq.company.domain.CompanyQuoteRequest;
import org.re.hq.company.repository.CompanyRepository;
import org.re.hq.employee.domain.EmployeeCredentials;
import org.re.hq.employee.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class CompanyService {
    private final EmployeeService employeeService;
    private final CompanyRepository companyRepository;

    public Page<Company> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
    }

    public Company findById(Long id) {
        return companyRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Company not found with id: " + id));
    }

    public boolean isBusinessNumberExists(String businessNumber) {
        return companyRepository.existsByBusinessNumber(businessNumber);
    }

    public Company createCompany(CompanyQuoteRequest quoteRequest) {
        var bisNo = quoteRequest.getQuoteInfo().getCompanyBusinessNumber();
        if (isBusinessNumberExists(bisNo)) {
            throw new IllegalArgumentException("Business number already exists: " + bisNo);
        }

        var company = quoteRequest.toCompany();
        companyRepository.save(company);
        var credential = EmployeeCredentials.from(quoteRequest);
        employeeService.createRootAccount(company.getId(), credential, "Root", "Administrator");
        return company;
    }
}
