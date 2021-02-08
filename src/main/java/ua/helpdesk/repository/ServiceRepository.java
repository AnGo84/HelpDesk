package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entities.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}
