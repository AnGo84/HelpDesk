package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.Ticket;

@Repository
public interface TicketRepository extends CommonRepository<Ticket> {
	Ticket findByNumber(String number);
}
