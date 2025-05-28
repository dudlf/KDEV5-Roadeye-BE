package org.re.hq.company.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.re.hq.company.dto.CompanyQuoteRequestCommandFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Import({CompanyQuoteService.class})
@DataJpaTest
class CompanyQuoteServiceTest {
    @Autowired
    CompanyQuoteService companyQuoteService;

    @MockitoBean
    CompanyService companyService;

    @Test
    @DisplayName("회사 견적 요청 생성에 성공해야 한다.")
    void requestNewQuote_Success() {
        // Given
        var command = CompanyQuoteRequestCommandFixture.create();
        Mockito.when(companyService.isBusinessNumberExists(command.businessNumber())).thenReturn(false);

        // When
        var quoteRequest = companyQuoteService.requestNewQuote(command);

        // Then
        assertNotNull(quoteRequest);
        assertEquals(command.name(), quoteRequest.getQuoteInfo().getCompanyName());
        assertEquals(command.username(), quoteRequest.getQuoteInfo().getRootAccountUsername());
        assertEquals(command.email(), quoteRequest.getQuoteInfo().getCompanyEmail());
        assertEquals(command.businessNumber(), quoteRequest.getQuoteInfo().getCompanyBusinessNumber());
        assertNotNull(quoteRequest.getRequestedAt());
    }
}
