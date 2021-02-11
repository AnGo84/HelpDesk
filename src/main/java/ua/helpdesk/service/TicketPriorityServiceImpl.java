package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.TicketPriority;
import ua.helpdesk.repository.TicketPriorityRepository;


@Service
@Transactional
//@RequiredArgsConstructor
@Slf4j
public class TicketPriorityServiceImpl extends AbstractService<TicketPriority, TicketPriorityRepository> {

    public TicketPriorityServiceImpl(TicketPriorityRepository repository) {
        super(repository);
    }

    /*private final TicketPriorityRepository repository;*/

    /*@Override
    public TicketPriority save(TicketPriority entity) {
        log.info("Save entity: {}", entity);
        TicketPriority savedEntity = repository.save(entity);
        return savedEntity;
    }

    @Override
    public List<TicketPriority> saveAll(List<TicketPriority> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public TicketPriority update(TicketPriority entity) {
        return repository.save(entity);
    }

    @Override
    public TicketPriority get(Long id) {
        Optional<TicketPriority> entity = repository.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        return null;
    }*/

    public TicketPriority findByName(String login) {
        return repository.findByName(login);
    }

    /*@Override
    public List<TicketPriority> getAll() {
        return repository.findAll();
    }

    @Override
    public Boolean deleteById(Long id) {
        TicketPriority entity = repository.findById(id)
                .orElseThrow(() -> new EntityException(String.format(EntityErrorType.ENTITY_NOT_FOUND_BY_ID.getDescription(), id)));
        repository.deleteById(id);
        return !repository.findById(entity.getId()).isPresent();
    }

    @Override
    public Boolean deleteAll() {
        repository.deleteAll();
        return repository.findAll().isEmpty();
    }*/

    @Override
    public Boolean isExist(TicketPriority entity) {
        log.debug("Check on exist: {}", entity);
        if (entity == null) {
            return false;
        }
        TicketPriority foundEntity = repository.findByName(entity.getName());
        if (foundEntity == null) {
            return false;
        } else if (entity.getId() == null || !entity.getId().equals(foundEntity.getId())) {
            return true;
        }
        return false;
    }
}
