package org.re.hq.company.converter;

import jakarta.persistence.Converter;
import org.re.hq.company.domain.CompanyQuoteStatus;
import org.re.hq.domain.common.AbstractPersistenceEnumConverter;

@Converter
public class CompanyQuoteStatusConverter extends AbstractPersistenceEnumConverter<CompanyQuoteStatus, Integer> {
    public CompanyQuoteStatusConverter() {
        super(CompanyQuoteStatus.class);
    }
}
