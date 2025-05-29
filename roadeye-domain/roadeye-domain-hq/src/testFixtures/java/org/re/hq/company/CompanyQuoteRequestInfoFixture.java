package org.re.hq.company;

import org.re.hq.company.domain.CompanyQuoteRequestInfo;

public class CompanyQuoteRequestInfoFixture {
    public static CompanyQuoteRequestInfo create() {
        String name = "Test Company";
        String username = "admin";
        String password = "password";
        String email = "test@test.com";
        String businessNumber = "1234567890";
        return new CompanyQuoteRequestInfo(
            name,
            username,
            password,
            email,
            businessNumber
        );
    }
}
