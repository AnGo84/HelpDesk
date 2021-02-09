package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findByName(String name);
}
