package org.re.company.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyQuoteCreationRequest(
    @NotBlank
    String companyName,
    @NotBlank
    String rootAccountUsername,
    @NotBlank
    String rootAccountPassword,
    @Email
    String companyEmail,
    @NotBlank
    String companyBusinessNumber
) {
    public CompanyQuoteRequestCommand toCommand() {
        return new CompanyQuoteRequestCommand(
            companyName,
            rootAccountUsername,
            rootAccountPassword,
            companyEmail,
            companyBusinessNumber
        );
    }
}
