package ru.kishko.deal.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.kishko.deal.annotations.ValidEnum;
import ru.kishko.deal.enums.EmploymentPosition;
import ru.kishko.deal.enums.EmploymentStatus;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmploymentDto {

    @ValidEnum(enumClass = EmploymentStatus.class)
    @NotNull(message = "Employment status must not be null")
    EmploymentStatus employmentStatus;

    @NotBlank(message = "Employer INN must not be blank")
    @Pattern(regexp = "[0-9]+", message = "Employer INN must consist of digits only")
    @Size(min = 10, max = 12, message = "Employer INN must be 10 or 12 digits")
    String employerINN;

    @NotNull(message = "Salary must not be null")
    @DecimalMin(value = "10000", message = "Salary must be greater than or equal to 10000")
    BigDecimal salary;

    @ValidEnum(enumClass = EmploymentPosition.class)
    @NotNull(message = "Position must not be null")
    EmploymentPosition position;

    @NotNull(message = "Total work experience must not be null")
    @Min(value = 0, message = "Total work experience must be greater than or equal to 0")
    Integer workExperienceTotal;

    @NotNull(message = "Work experience at current employer must not be null")
    @Min(value = 0, message = "Work experience at current employer must be greater than or equal to 0")
    Integer workExperienceCurrent;
}
