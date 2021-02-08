package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entities.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {
}
