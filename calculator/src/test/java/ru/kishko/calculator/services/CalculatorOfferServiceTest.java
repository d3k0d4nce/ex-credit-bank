package ru.kishko.calculator.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kishko.calculator.dtos.LoanOfferDto;
import ru.kishko.calculator.dtos.LoanStatementRequestDto;
import ru.kishko.calculator.services.Impl.CalculatorOfferServiceImpl;
import ru.kishko.calculator.services.utils.LoanCalculator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@WebMvcTest(CalculatorOfferService.class)
class CalculatorOfferServiceTest {

    @MockBean
    private LoanCalculator loanCalculator;

    @Autowired
    private CalculatorOfferServiceImpl calculatorOfferService;

    @Test
    void calculateLoanOffers_shouldReturnOffers() {
        // Заглушки для LoanCalculator
        LoanStatementRequestDto request = new LoanStatementRequestDto(BigDecimal.valueOf(100000), 12, "John", "Doe", "Smith", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234", "567890");
        when(loanCalculator.adjustInterestRate(any(), anyBoolean(), anyBoolean())).thenReturn(BigDecimal.valueOf(10.5));
        when(loanCalculator.calculatePrincipal(any(), anyBoolean())).thenReturn(BigDecimal.valueOf(100000));
        when(loanCalculator.calculateMonthlyPayment(any(), any(), anyInt())).thenReturn(BigDecimal.valueOf(8600));
        when(loanCalculator.calculateTotalAmount(any(), anyInt())).thenReturn(BigDecimal.valueOf(103200));

        // Выполнение метода и проверка результата
        List<LoanOfferDto> offers = calculatorOfferService.calculateLoanOffers(request);
        assertEquals(4, offers.size());

        // Проверка вызова заглушек
        verify(loanCalculator, times(4)).adjustInterestRate(any(), anyBoolean(), anyBoolean());
        verify(loanCalculator, times(4)).calculatePrincipal(any(), anyBoolean());
        verify(loanCalculator, times(4)).calculateMonthlyPayment(any(), any(), anyInt());
        verify(loanCalculator, times(4)).calculateTotalAmount(any(), anyInt());
    }
}
