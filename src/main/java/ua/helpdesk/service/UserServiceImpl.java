package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.User;
import ua.helpdesk.entity.UserType;
import ua.helpdesk.repository.UserRepository;


@Service
@Transactional
@Slf4j
public class UserServiceImpl extends AbstractService<User, UserRepository> {

	@Value("${user.password.default:123}")
	private String userPasswordDefault;

	@Autowired
	private final BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository repository, BCryptPasswordEncoder passwordEncoder) {
		super(repository);
		this.passwordEncoder = passwordEncoder;
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

	public User createDefaultInstance() {
		log.debug("Create new USER");
		User newUser = new User();
		newUser.setUserType(UserType.USER);
		newUser.setActive(true);
		newUser.setPassword(passwordEncoder.encode(userPasswordDefault));
		return newUser;
	}

	public User resetPassword(User user) {
		log.debug("Reset user password");
		user.setPassword(passwordEncoder.encode(userPasswordDefault));
		return update(user);
	}

	public User updatePassword(User user, String password) {
		log.debug("Reset user password");
		user.setPassword(passwordEncoder.encode(password));
		return update(user);
	}
}
