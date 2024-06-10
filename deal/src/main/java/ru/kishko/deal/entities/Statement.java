package ru.kishko.deal.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;
import ru.kishko.deal.dtos.LoanOfferDto;
import ru.kishko.deal.dtos.jsonb.StatusHistoryJsonb;
import ru.kishko.deal.enums.ApplicationStatus;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "statement")
public class Statement {

    @Id
    @Column(name = "statement_id")
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID statementId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "credit_id", referencedColumnName = "credit_id")
    private Credit credit;

    @Column(name = "status")
    @Enumerated(value = EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column(name = "creation_date", updatable = false)
    private Date creationDate;

    @Embedded
    @Column(name = "applied_offer", columnDefinition = "jsonb")
    private LoanOfferDto appliedOffer;

    @Column(name = "sign_date")
    private Timestamp signDate;

    @Column(name = "ses_code")
    private Integer sesCode;

    @ElementCollection
    @Column(name = "status_history", columnDefinition = "jsonb")
    private List<StatusHistoryJsonb> statusHistory;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Statement statement = (Statement) o;
        return statementId != null && Objects.equals(statementId, statement.statementId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
