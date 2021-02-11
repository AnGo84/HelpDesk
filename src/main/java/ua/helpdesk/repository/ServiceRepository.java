package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.Service;

@Repository
public interface ServiceRepository extends CommonRepository<Service> {
    Service findByName(String name);
}
