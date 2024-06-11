package ru.kishko.deal.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kishko.deal.annotations.ValidEnum;
import ru.kishko.deal.enums.Gender;
import ru.kishko.deal.enums.MaritalStatus;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinishRegistrationRequestDto {

    @ValidEnum(enumClass = Gender.class)
    @NotNull(message = "Gender must not be null")
    Gender gender;

    @ValidEnum(enumClass = MaritalStatus.class)
    @NotNull(message = "MaritalStatus must not be null")
    MaritalStatus maritalStatus;

    @Min(value = 0, message = "Dependent amount must be greater than or equal to 0")
    Integer dependentAmount;

    @NotNull(message = "Passport issue date must not be null")
    @Past(message = "Passport issue date must be in the past")
    LocalDate passportIssueDate;

    @NotBlank(message = "Passport issue branch must not be blank")
    @Size(max = 100, message = "Passport issue branch must not exceed 100 characters")
    String passportIssueBranch;

    @Valid
    @NotNull(message = "Employment information must not be null")
    EmploymentDto employment;

    @NotBlank(message = "Account number must not be blank")
    @Pattern(regexp = "[0-9]+", message = "Account number must consist of digits only")
    @Size(min = 16, max = 20, message = "Account number must be between 16 and 20 digits")
    String accountNumber;

}
