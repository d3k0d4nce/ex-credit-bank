package ru.kishko.deal.dtos.jsonb;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class EmploymentJsonb {
    private UUID employmentUUID;
    private String employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private String employmentPosition;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;
}
