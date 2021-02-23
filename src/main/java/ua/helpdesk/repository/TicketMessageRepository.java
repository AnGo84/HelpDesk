package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.Ticket;
import ua.helpdesk.entity.TicketMessage;

import java.util.List;

@Repository
public interface TicketMessageRepository extends CommonRepository<TicketMessage> {
    List<TicketMessage> findByTicketOrderByDateDesc(Ticket ticket);
}
