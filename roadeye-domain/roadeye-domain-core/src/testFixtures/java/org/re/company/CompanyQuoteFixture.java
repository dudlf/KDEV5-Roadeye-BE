package org.re.company;

import org.re.company.domain.CompanyQuote;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

public class CompanyQuoteFixture {
    public static CompanyQuote create() {
        var quoteInfo = CompanyQuoteInfoFixture.create();
        var requestedAt = LocalDateTime.now();
        return new CompanyQuote(quoteInfo, requestedAt);
    }

    public static List<CompanyQuote> createList(int nQuotes) {
        return IntStream.range(0, nQuotes)
            .mapToObj(i -> create())
            .toList();
    }
}
