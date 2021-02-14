package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.User;
import ua.helpdesk.repository.UserRepository;


@Service
@Transactional
@Slf4j
public class UserServiceImpl extends AbstractService<User, UserRepository> {

	public UserServiceImpl(UserRepository repository) {
		super(repository);
	}

	public User findByLogin(String login) {
		return repository.findByLogin(login);
	}

	@Override
	public Boolean isExist(User entity) {
		log.debug("Check on exist: {}", entity);
		if (entity == null) {
			return false;
		}
		User foundEntity = repository.findByLogin(entity.getLogin());
		if (foundEntity == null) {
			return false;
		} else return entity.getId() == null || !entity.getId().equals(foundEntity.getId());
	}

}
