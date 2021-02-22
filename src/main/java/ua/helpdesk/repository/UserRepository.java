package ua.helpdesk.repository;

import org.springframework.stereotype.Repository;
import ua.helpdesk.entity.User;
import ua.helpdesk.entity.UserType;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends CommonRepository<User> {
	User findByLogin(String username);

	List<User> findByUserTypeIn(Collection<UserType> userTypes);
}
