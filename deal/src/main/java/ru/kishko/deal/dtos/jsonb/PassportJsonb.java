package ru.kishko.deal.dtos.jsonb;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Embeddable;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class PassportJsonb {
    private UUID passportUUID;
    private String series;
    private String number;
    private String issueBranch;
    private Date issueDate;
}
