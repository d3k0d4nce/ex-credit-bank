package ru.kishko.calculator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.kishko.calculator.dtos.*;
import ru.kishko.calculator.enums.EmploymentStatus;
import ru.kishko.calculator.enums.Gender;
import ru.kishko.calculator.enums.MaritalStatus;
import ru.kishko.calculator.enums.Position;
import ru.kishko.calculator.services.CalculatorCreditService;
import ru.kishko.calculator.services.CalculatorOfferService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CalculatorOfferService calculatorOfferService;

    @MockBean
    private CalculatorCreditService calculatorCreditService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculateLoanOffers_shouldReturnOffers() throws Exception {
        // Заглушка для CalculatorOfferService
        LoanStatementRequestDto request = new LoanStatementRequestDto(BigDecimal.valueOf(100000), 12, "John", "Doe", "Smith", "john.doe@example.com", LocalDate.of(1990, 1, 1), "1234", "567890");
        List<LoanOfferDto> offers = new ArrayList<>();
        offers.add(LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .term(12)
                .requestedAmount(BigDecimal.valueOf(100000))
                .monthlyPayment(BigDecimal.valueOf(8600))
                .rate(BigDecimal.valueOf(10.5))
                .totalAmount(BigDecimal.valueOf(103200))
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .build());
        offers.add(LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .term(12)
                .requestedAmount(BigDecimal.valueOf(100000))
                .monthlyPayment(BigDecimal.valueOf(8800))
                .rate(BigDecimal.valueOf(11))
                .totalAmount(BigDecimal.valueOf(105600))
                .isSalaryClient(false)
                .isInsuranceEnabled(true)
                .build());
        when(calculatorOfferService.calculateLoanOffers(request)).thenReturn(offers);

        // Выполнение запроса и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].monthlyPayment").value(8600))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].monthlyPayment").value(8800));

        // Проверка вызова заглушки
        verify(calculatorOfferService, times(1)).calculateLoanOffers(request);
    }

    @Test
    void calculateCredit_shouldReturnCredit() throws Exception {

        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EmploymentStatus.EMPLOYED)
                .employerINN("1234567890")
                .salary(BigDecimal.valueOf(50000))
                .position(Position.MANAGER)
                .workExperienceTotal(5)
                .workExperienceCurrent(2)
                .build();

        // Заглушка для CalculatorCreditService
        ScoringDataDto request = new ScoringDataDto(BigDecimal.valueOf(100000), 12, "John", "Doe", "Smith", Gender.MALE, LocalDate.of(1990, 1, 1), "1234", "567890", LocalDate.of(2020, 1, 1), "Branch", MaritalStatus.MARRIED, 2, employmentDto, "1234567890123456", true, true);
        CreditDto credit = CreditDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .rate(BigDecimal.valueOf(10.5))
                .term(12)
                .isSalaryClient(true)
                .isInsuranceEnabled(true)
                .monthlyPayment(BigDecimal.valueOf(8600))
                .psk(BigDecimal.valueOf(103200))
                .paymentSchedule(List.of(
                        PaymentScheduleElementDto.builder()
                                .number(1)
                                .date(LocalDate.now().plusMonths(1))
                                .totalPayment(BigDecimal.valueOf(8600))
                                .interestPayment(BigDecimal.valueOf(875))
                                .debtPayment(BigDecimal.valueOf(7725))
                                .remainingDebt(BigDecimal.valueOf(92275))
                                .build()
                ))
                .build();
        when(calculatorCreditService.calculateCredit(request)).thenReturn(credit);

        // Выполнение запроса и проверка результата
        mockMvc.perform(MockMvcRequestBuilders.post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.monthlyPayment").value(8600))
                .andExpect(MockMvcResultMatchers.jsonPath("$.psk").value(103200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSchedule[0].number").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paymentSchedule[0].interestPayment").value(875));

        // Проверка вызова заглушки
        verify(calculatorCreditService, times(1)).calculateCredit(request);
    }
}
