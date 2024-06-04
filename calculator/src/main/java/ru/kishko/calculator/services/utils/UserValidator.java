package ru.kishko.calculator.services.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.kishko.calculator.dtos.CreditDto;
import ru.kishko.calculator.dtos.ScoringDataDto;
import ru.kishko.calculator.enums.EmploymentStatus;
import ru.kishko.calculator.enums.Gender;
import ru.kishko.calculator.enums.MaritalStatus;
import ru.kishko.calculator.enums.Position;
import ru.kishko.calculator.exceptions.CreditException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Component
public class UserValidator {

    public void validate(ScoringDataDto data, CreditDto creditDto) {
        log.info("Starting validation for scoring data: {}", data);

        checkEmploymentStatus(data, creditDto);
        checkPosition(data, creditDto);
        checkLoanAmount(data);
        checkMaritalStatus(data, creditDto);
        checkAge(data);
        checkGender(data, creditDto);
        checkWorkExperience(data);

        log.info("Validation completed successfully");
    }

    private void checkEmploymentStatus(ScoringDataDto data, CreditDto creditDto) {
        log.debug("Checking employment status: {}", data.getEmployment().getEmploymentStatus());
        EmploymentStatus employmentStatus = data.getEmployment().getEmploymentStatus();

        switch (employmentStatus) {
            case UNEMPLOYED -> throw new CreditException("Refusal due to unemployed");
            case SELF_EMPLOYED -> creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(1)));
            case BUSINESS_OWNER -> creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(2)));
        }
    }

    private void checkPosition(ScoringDataDto data, CreditDto creditDto) {
        log.debug("Checking position: {}", data.getEmployment().getPosition());
        Position position = data.getEmployment().getPosition();

        switch (position) {
            case MANAGER -> creditDto.setRate(creditDto.getRate().subtract(BigDecimal.valueOf(2)));
            case TOP_MANAGER -> creditDto.setRate(creditDto.getRate().subtract(BigDecimal.valueOf(3)));
        }
    }

    private void checkLoanAmount(ScoringDataDto data) {
        log.debug("Checking loan amount: {}", data.getAmount());
        if (data.getAmount().compareTo(data.getEmployment().getSalary().multiply(new BigDecimal("25"))) > 0) {
            throw new CreditException("Loan amount must not be greater than 25 times the salary.");
        }
    }

    private void checkMaritalStatus(ScoringDataDto data, CreditDto creditDto) {
        log.debug("Checking marital status: {}", data.getMaritalStatus());
        MaritalStatus maritalStatus = data.getMaritalStatus();

        switch (maritalStatus) {
            case MARRIED -> creditDto.setRate(creditDto.getRate().subtract(BigDecimal.valueOf(3)));
            case DIVORCED -> creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(1)));
        }
    }

    private void checkAge(ScoringDataDto data) {
        log.debug("Checking age: {}", data.getBirthdate());
        int age = Period.between(data.getBirthdate(), LocalDate.now()).getYears();
        if (age < 20 || age > 65) {
            throw new CreditException("Age must be between 20 and 65 years old.");
        }
    }

    private void checkGender(ScoringDataDto data, CreditDto creditDto) {
        log.debug("Checking gender: {}", data.getGender());
        Gender gender = data.getGender();
        int age = Period.between(data.getBirthdate(), LocalDate.now()).getYears();

        if (gender == Gender.NON_BINARY) creditDto.setRate(creditDto.getRate().add(BigDecimal.valueOf(7)));
        if (gender == Gender.FEMALE && (age >= 32 && age < 60)) creditDto.setRate(creditDto.getRate().subtract(BigDecimal.valueOf(3)));
        if (gender == Gender.MALE && (age >= 30 && age < 55)) creditDto.setRate(creditDto.getRate().subtract(BigDecimal.valueOf(3)));
    }

    private void checkWorkExperience(ScoringDataDto data) {
        log.debug("Checking work experience: total={}, current={}", data.getEmployment().getWorkExperienceTotal(), data.getEmployment().getWorkExperienceCurrent());
        if (data.getEmployment().getWorkExperienceTotal() < 18) {
            throw new CreditException("Total work experience must be at least 18 months.");
        }
        if (data.getEmployment().getWorkExperienceCurrent() < 3) {
            throw new CreditException("Work experience at current employer must be at least 3 months.");
        }
    }
}
