package ru.kishko.calculator.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.kishko.calculator.dtos.CreditDto;
import ru.kishko.calculator.dtos.EmploymentDto;
import ru.kishko.calculator.dtos.ScoringDataDto;
import ru.kishko.calculator.enums.EmploymentStatus;
import ru.kishko.calculator.enums.Gender;
import ru.kishko.calculator.enums.MaritalStatus;
import ru.kishko.calculator.enums.Position;
import ru.kishko.calculator.exceptions.CreditException;
import ru.kishko.calculator.services.utils.UserValidator;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;

@WebMvcTest(UserValidator.class)
class UserValidatorTest {

    private final UserValidator validator = new UserValidator();

    @Test
    void validate_shouldThrowExceptionForUnemployed() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.UNEMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);

        Exception exception = assertThrows(CreditException.class, () -> validator.validate(data, creditDto));
        assertEquals("Refusal due to unemployed", exception.getMessage());
    }

    @Test
    void validate_shouldAdjustRateForSelfEmployed() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.SELF_EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(1)).setRate(BigDecimal.valueOf(11));
    }

    @Test
    void validate_shouldAdjustRateForBusinessOwner() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.BUSINESS_OWNER, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(1)).setRate(BigDecimal.valueOf(12));
    }

    @Test
    void validate_shouldAdjustRateForManager() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(1)).setRate(BigDecimal.valueOf(8));
    }

    @Test
    void validate_shouldAdjustRateForTopManager() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.TOP_MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(3)).setRate(BigDecimal.valueOf(7));
    }

    @Test
    void validate_shouldThrowExceptionForInvalidLoanAmount() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 2, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        // Установите начальное значение для creditDto.getRate()
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        Exception exception = assertThrows(CreditException.class, () -> validator.validate(data, creditDto));
        assertEquals("Loan amount must not be greater than 25 times the salary.", exception.getMessage());
    }

    @Test
    void validate_shouldAdjustRateForMarried() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(2)).setRate(BigDecimal.valueOf(7));
    }

    @Test
    void validate_shouldAdjustRateForDivorced() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.DIVORCED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(1)).setRate(BigDecimal.valueOf(11));
    }

    @Test
    void validate_shouldThrowExceptionForInvalidAge() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(2005, 1, 1), 15, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        // Установите начальное значение для creditDto.getRate()
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        Exception exception = assertThrows(CreditException.class, () -> validator.validate(data, creditDto));
        assertEquals("Age must be between 20 and 65 years old.", exception.getMessage());
    }

    @Test
    void validate_shouldThrowExceptionForInvalidAge2() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1940, 1, 1), 80, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        // Установите начальное значение для creditDto.getRate()
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        Exception exception = assertThrows(CreditException.class, () -> validator.validate(data, creditDto));
        assertEquals("Age must be between 20 and 65 years old.", exception.getMessage());
    }

    @Test
    void validate_shouldAdjustRateForNonBinary() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.NON_BINARY, LocalDate.of(1990, 1, 1), 20, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(1)).setRate(BigDecimal.valueOf(17));
    }

    @Test
    void validate_shouldAdjustRateForFemaleWithinAgeRange() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.FEMALE, LocalDate.of(1980, 1, 1), 40, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(2)).setRate(BigDecimal.valueOf(7));
    }

    @Test
    void validate_shouldAdjustRateForMaleWithinAgeRange() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1985, 1, 1), 35, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        validator.validate(data, creditDto);
        Mockito.verify(creditDto, times(2)).setRate(BigDecimal.valueOf(7));
    }

    @Test
    void validate_shouldThrowExceptionForInvalidWorkExperienceTotal() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 15, 5);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        // Установите начальное значение для creditDto.getRate()
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        Exception exception = assertThrows(CreditException.class, () -> validator.validate(data, creditDto));
        assertEquals("Total work experience must be at least 18 months.", exception.getMessage());
    }

    @Test
    void validate_shouldThrowExceptionForInvalidWorkExperienceCurrent() {
        ScoringDataDto data = createScoringDataDto(EmploymentStatus.EMPLOYED, Position.MANAGER, 100000, 25000, MaritalStatus.MARRIED, Gender.MALE, LocalDate.of(1990, 1, 1), 20, 2);
        CreditDto creditDto = Mockito.mock(CreditDto.class);
        // Установите начальное значение для creditDto.getRate()
        Mockito.when(creditDto.getRate()).thenReturn(BigDecimal.valueOf(10));

        Exception exception = assertThrows(CreditException.class, () -> validator.validate(data, creditDto));
        assertEquals("Work experience at current employer must be at least 3 months.", exception.getMessage());
    }

    private ScoringDataDto createScoringDataDto(EmploymentStatus employmentStatus, Position position, int amount, int salary, MaritalStatus maritalStatus, Gender gender, LocalDate birthdate, int workExperienceTotal, int workExperienceCurrent) {
        return ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(amount))
                .term(12)
                .firstName("John")
                .lastName("Doe")
                .middleName("Smith")
                .gender(gender)
                .birthdate(birthdate)
                .passportSeries("1234")
                .passportNumber("567890")
                .passportIssueDate(LocalDate.of(2020, 1, 1))
                .passportIssueBranch("Branch")
                .maritalStatus(maritalStatus)
                .dependentAmount(2)
                .employment(EmploymentDto.builder()
                        .employmentStatus(employmentStatus)
                        .employerINN("1234567890")
                        .salary(BigDecimal.valueOf(salary))
                        .position(position)
                        .workExperienceTotal(workExperienceTotal)
                        .workExperienceCurrent(workExperienceCurrent)
                        .build())
                .accountNumber("1234567890123456")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
    }
}
