package ru.kishko.deal.dtos.jsonb;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import org.hibernate.annotations.TypeDef;
import ru.kishko.deal.enums.ChangeType;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@ToString
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class StatusHistoryJsonb {
    private String status;
    private Timestamp timestamp;
    private ChangeType changeType;
}
