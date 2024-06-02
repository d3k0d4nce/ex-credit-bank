package ru.kishko.calculator.services.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class LoanCalculator {

    @Value("${insurance.cost}")
    private BigDecimal INSURANCE_COST;
    @Value("${insurance.discount}")
    private BigDecimal INSURANCE_DISCOUNT;
    @Value("${salary.client.discount}")
    private BigDecimal SALARY_CLIENT_DISCOUNT;

    public BigDecimal calculateMonthlyPayment(BigDecimal loanAmount, BigDecimal interestRate, int term) {
        BigDecimal monthlyInterestRate = interestRate.divide(new BigDecimal(12 * 100), 10, RoundingMode.HALF_UP);
        BigDecimal x = loanAmount.multiply(
                monthlyInterestRate.add(
                        monthlyInterestRate.divide((monthlyInterestRate.add(BigDecimal.ONE))
                                .pow(term).subtract(BigDecimal.ONE), 10, RoundingMode.HALF_UP)
                )
        );
        return x.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculatePrincipal(BigDecimal amount, boolean isInsuranceEnabled) {
        return isInsuranceEnabled ? amount.add(INSURANCE_COST) : amount;
    }

    public BigDecimal adjustInterestRate(BigDecimal interestRate, boolean isInsuranceEnabled, boolean isSalaryClient) {
        if (isInsuranceEnabled) interestRate = interestRate.subtract(INSURANCE_DISCOUNT);
        if (isSalaryClient) interestRate = interestRate.subtract(SALARY_CLIENT_DISCOUNT);
        return interestRate;
    }

    public BigDecimal calculateTotalAmount(BigDecimal monthlyPayment, int term) {
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }
}