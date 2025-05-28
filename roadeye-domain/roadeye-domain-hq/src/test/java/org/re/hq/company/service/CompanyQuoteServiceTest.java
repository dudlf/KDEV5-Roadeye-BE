package org.re.hq.company.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.re.hq.company.domain.CompanyQuoteStatus;
import org.re.hq.company.dto.CompanyQuoteRequestCommandFixture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
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

    @Nested
    @DisplayName("조회 테스트")
    class SelectTest {
        @Test
        @DisplayName("견적 목록 조회가 가능해야 한다.")
        void findAll_Success() {
            // Given
            var numQuotes = 5;
            for (int i = 0; i < numQuotes; i++) {
                var command = CompanyQuoteRequestCommandFixture.create();
                Mockito.when(companyService.isBusinessNumberExists(command.businessNumber())).thenReturn(false);
                companyQuoteService.requestNewQuote(command);
            }
            var pageable = PageRequest.of(0, 10);

            // When
            var quoteRequests = companyQuoteService.findAll(pageable);

            // Then
            assertNotNull(quoteRequests);
            assertEquals(numQuotes, quoteRequests.getTotalElements());
        }

        @Test
        @DisplayName("견적 단건 조회가 가능해야 한다.")
        void findById_Success() {
            // Given
            var command = CompanyQuoteRequestCommandFixture.create();
            Mockito.when(companyService.isBusinessNumberExists(command.businessNumber())).thenReturn(false);
            var quoteRequest = companyQuoteService.requestNewQuote(command);

            // When
            var foundQuoteRequest = companyQuoteService.findById(quoteRequest.getId());

            // Then
            assertNotNull(foundQuoteRequest);
            assertEquals(quoteRequest.getId(), foundQuoteRequest.getId());
        }

        @Test
        @DisplayName("견적 상태별 목록 조회가 가능해야 한다.")
        void findAllByQuoteStatus_Success() {
            // Given
            var numQuotes = 5;
            for (int i = 0; i < numQuotes; i++) {
                var command = CompanyQuoteRequestCommandFixture.create();
                Mockito.when(companyService.isBusinessNumberExists(command.businessNumber())).thenReturn(false);
                companyQuoteService.requestNewQuote(command);
            }
            var quoteStatus = CompanyQuoteStatus.PENDING;
            var pageable = PageRequest.of(0, 10);

            // When
            var quoteRequests = companyQuoteService.findAllByQuoteStatus(quoteStatus, pageable);

            // Then
            assertNotNull(quoteRequests);
            assertEquals(numQuotes, quoteRequests.getTotalElements());
        }
    }

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
