package ru.kishko.deal.dtos.jsonb;

import lombok.*;
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
public class StatusHistoryJsonb {
    private String status;
    private Timestamp timestamp;
    private ChangeType changeType;
}
