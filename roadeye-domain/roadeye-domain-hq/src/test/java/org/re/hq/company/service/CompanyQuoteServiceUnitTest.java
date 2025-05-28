package org.re.hq.company.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.re.hq.admin.domain.PlatformAdmin;
import org.re.hq.company.CompanyQuoteRequestFixture;
import org.re.hq.company.repository.CompanyQuoteRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CompanyQuoteServiceUnitTest {
    @Mock
    CompanyQuoteRepository companyQuoteRepository;

    @Mock
    CompanyService companyService;

    @InjectMocks
    CompanyQuoteService companyQuoteService;

    @Nested
    @DisplayName("승인 테스트")
    class ApprovalTest {
        @Test
        @DisplayName("견적 요청을 승인할 수 있어야 한다.")
        void approve_Success() {
            // Given
            var quoteRequest = Mockito.spy(CompanyQuoteRequestFixture.create());
            var approver = Mockito.mock(PlatformAdmin.class);

            // When
            var approvedQuoteRequest = companyQuoteService.approve(approver, quoteRequest);

            // Then
            assertNotNull(approvedQuoteRequest);
            Mockito.verify(quoteRequest).approve(approver);
            assertThat(approvedQuoteRequest.getApprover()).isEqualTo(approver);
        }
        
        @Test
        @DisplayName("이미 승인된 견적 요청을 다시 승인할 수 없어야 한다.")
        void approve_AlreadyApproved_ThrowsException() {
            // Given
            var quoteRequest = Mockito.spy(CompanyQuoteRequestFixture.create());
            var approver = Mockito.mock(PlatformAdmin.class);
            quoteRequest.approve(approver); // 이미 승인된 상태

            // When & Then
            assertThrows(RuntimeException.class, () -> {
                companyQuoteService.approve(approver, quoteRequest);
            });
        }
    }
}
