package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entities.TicketMessage;

@Repository
public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {
}
