package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.helpdesk.entities.User;


public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
