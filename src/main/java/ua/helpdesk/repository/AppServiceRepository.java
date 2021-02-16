package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.AppService;

@Repository
public interface AppServiceRepository extends CommonRepository<AppService> {
	AppService findByName(String name);
}
