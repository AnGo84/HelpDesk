package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.TicketPriority;
import ua.helpdesk.repository.TicketPriorityRepository;


@Service
@Transactional
@Slf4j
public class TicketPriorityServiceImpl extends AbstractService<TicketPriority, TicketPriorityRepository> {

    public TicketPriorityServiceImpl(TicketPriorityRepository repository) {
        super(repository);
    }


    public TicketPriority findByName(String name) {
        log.debug("Find by name: {}", name);
        return repository.findByName(name);
    }

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
