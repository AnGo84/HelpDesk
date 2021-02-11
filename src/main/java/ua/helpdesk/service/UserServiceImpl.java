package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    //private final UserRepository userRepository;

    public String getPrincipal() {
        String userName;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    /*@Override
    public User save(User entity) {
        log.info("Save entity: {}", entity);
        User savedEntity = userRepository.save(entity);
        return savedEntity;
    }

    @Override
    public User update(User entity) {
        return userRepository.save(entity);
    }

    @Override
    public User get(Long id) {
        Optional<User> entity = userRepository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        return null;
    }*/

    public User findByLogin(String login) {
        return repository.findByLogin(login);
    }

    /*@Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean deleteById(Long id) {
        User entity = userRepository.findById(id)
                .orElseThrow(() -> new EntityException(String.format(EntityErrorType.ENTITY_NOT_FOUND_BY_ID.getDescription(), id)));
        userRepository.deleteById(id);
        return !userRepository.findById(entity.getId()).isPresent();
    }

    @Override
    public Boolean deleteAll() {
        userRepository.deleteAll();
        return userRepository.findAll().isEmpty();
    }*/

    @Override
    public Boolean isExist(User entity) {
        log.debug("Check on exist: {}", entity);
        if (entity == null) {
            return false;
        }
        User foundEntity = repository.findByLogin(entity.getLogin());
        if (foundEntity == null) {
            return false;
        } else if (entity.getId() == null || !entity.getId().equals(foundEntity.getId())) {
            return true;
        }
        return false;
    }
}
