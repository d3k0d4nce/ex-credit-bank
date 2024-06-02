package ru.kishko.calculator.services;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kishko.calculator.dtos.CreditDto;
import ru.kishko.calculator.dtos.EmploymentDto;
import ru.kishko.calculator.dtos.PaymentScheduleElementDto;
import ru.kishko.calculator.dtos.ScoringDataDto;
import ru.kishko.calculator.enums.EmploymentStatus;
import ru.kishko.calculator.enums.Gender;
import ru.kishko.calculator.enums.MaritalStatus;
import ru.kishko.calculator.enums.Position;
import ru.kishko.calculator.services.Impl.CalculatorCreditServiceImpl;
import ru.kishko.calculator.services.utils.LoanCalculator;
import ru.kishko.calculator.services.utils.UserValidator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CalculatorCreditService.class)
class CalculatorCreditServiceTest {

    @MockBean
    private UserValidator userValidator;

    @MockBean
    private LoanCalculator loanCalculator;

    @Autowired
    private CalculatorCreditServiceImpl calculatorCreditService;

    EmploymentDto employmentDto = EmploymentDto.builder()
            .employmentStatus(EmploymentStatus.EMPLOYED)
            .employerINN("1234567890")
            .salary(BigDecimal.valueOf(50000))
            .position(Position.MANAGER)
            .workExperienceTotal(5)
            .workExperienceCurrent(2)
            .build();

    @Test
    @Disabled
    void calculateCredit_shouldReturnCredit() {
        // Заглушки для UserValidator и LoanCalculator
        ScoringDataDto request = new ScoringDataDto(BigDecimal.valueOf(100000), 12, "John", "Doe", "Smith", Gender.MALE, LocalDate.of(1990, 1, 1), "1234", "567890", LocalDate.of(2020, 1, 1), "Branch", MaritalStatus.MARRIED, 2, employmentDto, "1234567890123456", true, true);
        CreditDto credit = CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .rate(BigDecimal.valueOf(10.5))
                .term(12)
                .psk(BigDecimal.valueOf(103200))
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .build();
//        when(loanCalculator.adjustInterestRate(any(), anyBoolean(), anyBoolean())).thenReturn(BigDecimal.valueOf(10.5));
        when(loanCalculator.calculatePrincipal(any(), anyBoolean())).thenReturn(BigDecimal.valueOf(100000));
        when(loanCalculator.calculateMonthlyPayment(any(), any(), anyInt())).thenReturn(BigDecimal.valueOf(8600));
        when(loanCalculator.calculateTotalAmount(any(), anyInt())).thenReturn(BigDecimal.valueOf(103200));

        // Выполнение метода и проверка результата
        CreditDto result = calculatorCreditService.calculateCredit(request);
        assertEquals(BigDecimal.valueOf(100000), result.getAmount());
        assertEquals(BigDecimal.valueOf(10.5), result.getRate());
        assertEquals(12, result.getTerm());
        assertEquals(BigDecimal.valueOf(8600), result.getMonthlyPayment());
        assertEquals(BigDecimal.valueOf(103200), result.getPsk());

        // Проверка вызова заглушек
        verify(userValidator, times(1)).validate(request, credit);
        verify(loanCalculator, times(1)).adjustInterestRate(any(), anyBoolean(), anyBoolean());
        verify(loanCalculator, times(1)).calculatePrincipal(any(), anyBoolean());
        verify(loanCalculator, times(1)).calculateMonthlyPayment(any(), any(), anyInt());
        verify(loanCalculator, times(1)).calculateTotalAmount(any(), anyInt());
    }

    @Test
    void calculatePaymentSchedule_shouldReturnPaymentSchedule() {
        // Заглушки для UserValidator и LoanCalculator
        CreditDto credit = CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .rate(BigDecimal.valueOf(10.5))
                .term(12)
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .monthlyPayment(BigDecimal.valueOf(8600))
                .psk(BigDecimal.valueOf(103200))
                .build();

        // Выполнение метода и проверка результата
        List<PaymentScheduleElementDto> schedule = calculatorCreditService.calculatePaymentSchedule(credit);
        assertEquals(12, schedule.size());
        assertEquals(1, schedule.get(0).getNumber());
        assertEquals(LocalDate.now().plusMonths(1), schedule.get(0).getDate());
        assertEquals(BigDecimal.valueOf(8600), schedule.get(0).getTotalPayment());
        assertEquals(BigDecimal.valueOf(875), schedule.get(0).getInterestPayment().setScale(0));
        assertEquals(BigDecimal.valueOf(7725), schedule.get(0).getDebtPayment().setScale(0));
        assertEquals(BigDecimal.valueOf(92275), schedule.get(0).getRemainingDebt().setScale(0));
    }
}
