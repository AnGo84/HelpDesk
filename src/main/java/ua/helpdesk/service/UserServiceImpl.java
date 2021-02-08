package ua.helpdesk.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import ua.helpdesk.entities.User;
import ua.helpdesk.exception.EntityErrorType;
import ua.helpdesk.exception.EntityException;
import ua.helpdesk.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;


@Service("userService")
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements CommonService<User> {

    private final UserRepository userRepository;


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

    @Override
    public User save(User entity) {
        log.info("Save entity: {}", entity);
        User savedEntity = userRepository.save(entity);
        return savedEntity;
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        return userRepository.saveAll(entities);
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
    }

    @Override
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
    }

    @Override
    public Boolean isExist(User entity) {
        if (isNull(entity) || StringUtils.isEmpty(entity.getLogin())) {
            throw new EntityException(EntityErrorType.ENTITY_NOT_FOUND.getDescription());
        }
        return userRepository.findByLogin(entity.getLogin()) != null;
    }
}
