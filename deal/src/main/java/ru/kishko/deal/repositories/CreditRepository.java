package ru.kishko.deal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.deal.entities.Credit;

import java.util.UUID;

public interface CreditRepository extends JpaRepository<Credit, UUID> {
}
