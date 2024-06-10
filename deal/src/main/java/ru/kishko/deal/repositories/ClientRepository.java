package ru.kishko.deal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kishko.deal.entities.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
}
