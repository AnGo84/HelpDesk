package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.User;

@Repository
public interface UserRepository extends CommonRepository<User> {
	User findByLogin(String username);
}
