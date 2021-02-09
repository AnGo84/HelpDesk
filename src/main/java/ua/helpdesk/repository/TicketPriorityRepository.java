package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.TicketPriority;

@Repository
public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Long> {
    TicketPriority findByName(String name);
}
