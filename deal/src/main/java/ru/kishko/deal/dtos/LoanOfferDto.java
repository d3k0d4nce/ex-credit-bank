package ru.kishko.deal.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanOfferDto {

    @Column(name = "statement_id", insertable = false, updatable = false)
    @NotNull(message = "Statement ID cannot be null")
    UUID statementId;

    @NotNull(message = "Requested amount cannot be null")
    @DecimalMin(value = "30000", message = "Loan amount must be greater than or equal to 30000")
    BigDecimal requestedAmount;

    @NotNull(message = "Total amount cannot be null")
    BigDecimal totalAmount;

    @NotNull(message = "Term must not be null")
    @Min(value = 6, message = "Term must be greater than or equal to 6")
    Integer term;

    @NotNull(message = "Monthly payment cannot be null")
    @Min(value = 100, message = "Monthly payment must be at least 100")
    BigDecimal monthlyPayment;

    @NotNull(message = "Rate cannot be null")
    @Min(value = 0, message = "Rate must be at least 0%")
    @Max(value = 100, message = "Rate cannot exceed 100%")
    BigDecimal rate;

    @NotNull(message = "Insurance enabled flag must not be null")
    Boolean isInsuranceEnabled;

    @NotNull(message = "Salary client flag must not be null")
    Boolean isSalaryClient;

}
