package ru.kishko.deal.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kishko.deal.annotations.MinAge;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanStatementRequestDto {

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

    @Email(message = "Invalid email address")
    @NotBlank(message = "Email must not be blank")
    String email;

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
}
