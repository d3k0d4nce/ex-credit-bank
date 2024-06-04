package ru.kishko.calculator.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kishko.calculator.annotations.MinAge;
import ru.kishko.calculator.annotations.ValidEnum;
import ru.kishko.calculator.enums.Gender;
import ru.kishko.calculator.enums.MaritalStatus;
import ru.kishko.calculator.enums.Position;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScoringDataDto {

    @NotNull(message = "Loan amount must not be null")
    @DecimalMin(value = "30000", message = "Loan amount must be greater than or equal to 30000")
    BigDecimal amount;

    @NotNull(message = "Term must not be null")
    @Min(value = 6, message = "Term must be greater than or equal to 6")
    Integer term;

    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "First name must consist of 2 to 30 Latin letters")
    @NotBlank(message = "First name must not be blank")
    String firstName;

    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Last name must consist of 2 to 30 Latin letters")
    @NotBlank(message = "Last name must not be blank")
    String lastName;

    @Pattern(regexp = "^[a-zA-Z]{2,30}$", message = "Middle name must consist of 2 to 30 Latin letters")
    String middleName;

    @ValidEnum(enumClass = Gender.class)
    @NotNull(message = "Gender must not be null")
    Gender gender;

    @MinAge(value = 18)
    @NotNull(message = "Birthdate must not be null")
    LocalDate birthdate;

    @Pattern(regexp = "[0-9]+")
    @NotBlank(message = "Passport series must not be blank")
    @Size(min = 4, max = 4, message = "Passport series must consist of 4 digits")
    String passportSeries;

    @Pattern(regexp = "[0-9]+")
    @NotBlank(message = "Passport number must not be blank")
    @Size(min = 6, max = 6, message = "Passport number must consist of 6 digits")
    String passportNumber;

    @NotNull(message = "Passport issue date must not be null")
    @Past(message = "Passport issue date must be in the past")
    LocalDate passportIssueDate;

    @NotBlank(message = "Passport issue branch must not be blank")
    @Size(max = 100, message = "Passport issue branch must not exceed 100 characters")
    String passportIssueBranch;

    @ValidEnum(enumClass = MaritalStatus.class)
    @NotNull(message = "Marital status must not be null")
    MaritalStatus maritalStatus;

    @Min(value = 0, message = "Dependent amount must be greater than or equal to 0")
    Integer dependentAmount;

    @Valid
    @NotNull(message = "Employment information must not be null")
    EmploymentDto employment;

    @NotBlank(message = "Account number must not be blank")
    @Pattern(regexp = "[0-9]+", message = "Account number must consist of digits only")
    @Size(min = 16, max = 20, message = "Account number must be between 16 and 20 digits")
    String accountNumber;

    @NotNull(message = "Insurance enabled flag must not be null")
    Boolean isInsuranceEnabled;

    @NotNull(message = "Salary client flag must not be null")
    Boolean isSalaryClient;
}

