package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.TicketPriority;

@Repository
public interface TicketPriorityRepository extends CommonRepository<TicketPriority> {
    TicketPriority findByName(String name);
}
