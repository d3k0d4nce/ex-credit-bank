package ru.kishko.deal.dtos.jsonb;

import lombok.*;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentJsonb {
    private String employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private String employmentPosition;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
