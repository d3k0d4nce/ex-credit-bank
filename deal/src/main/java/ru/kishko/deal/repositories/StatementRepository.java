package ru.kishko.deal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.deal.entities.Statement;

import java.util.UUID;

public interface StatementRepository extends JpaRepository<Statement, UUID> {
}
