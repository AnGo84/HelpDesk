package ua.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByLogin(String username);
}
