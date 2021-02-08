package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entities.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
