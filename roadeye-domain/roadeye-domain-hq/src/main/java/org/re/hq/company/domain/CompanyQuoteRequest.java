package org.re.hq.company.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.re.hq.company.converter.CompanyQuoteStatusConverter;
import org.re.hq.domain.common.BaseEntity;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompanyQuoteRequest extends BaseEntity {
    @Convert(converter = CompanyQuoteStatusConverter.class)
    @Column(nullable = false)
    private CompanyQuoteStatus quoteStatus;

    @Embedded
    private CompanyQuoteRequestInfo quoteInfo;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    @Column
    private LocalDateTime approvedAt;

    @Column
    private LocalDateTime rejectedAt;

    @Column
    private String rejectionReason;

    public CompanyQuoteRequest(CompanyQuoteRequestInfo quoteInfo, LocalDateTime requestedAt) {
        this.quoteStatus = CompanyQuoteStatus.PENDING;
        this.quoteInfo = quoteInfo;
        this.requestedAt = requestedAt;
    }
}
