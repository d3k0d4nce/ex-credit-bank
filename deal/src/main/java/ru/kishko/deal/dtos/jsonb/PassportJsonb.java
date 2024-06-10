package ru.kishko.deal.dtos.jsonb;

import lombok.*;

import javax.persistence.Embeddable;
import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PassportJsonb {
    private String series;
    private String number;
    private String issueBranch;
    private Date issueDate;
}
