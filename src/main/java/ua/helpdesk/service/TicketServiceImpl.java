package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.Ticket;
import ua.helpdesk.entity.TicketState;
import ua.helpdesk.exception.ForbiddenOperationException;
import ua.helpdesk.repository.TicketRepository;


@Service
@Transactional
@Slf4j
public class TicketServiceImpl extends AbstractService<Ticket, TicketRepository> {

    public TicketServiceImpl(TicketRepository repository) {
        super(repository);
    }


    public Ticket findByNumber(String name) {
        log.debug("Find by number: {}", name);
        return repository.findByNumber(name);
    }

    @Override
    public Boolean isExist(Ticket entity) {
        log.debug("Check on exist: {}", entity);
        if (entity == null) {
            return false;
        }
        Ticket foundEntity = repository.findByNumber(entity.getNumber());
        if (foundEntity == null) {
            return false;
        } else if (entity.getId() == null || !entity.getId().equals(foundEntity.getId())) {
            return true;
        }
        return false;
    }

    public Ticket createDefaultInstance() {
        log.debug("Create new ticket");
        Ticket newTicket = new Ticket();
        newTicket.setTicketState(TicketState.NEW);
        return newTicket;
    }

    @Override
    public Boolean deleteById(Long id) {
        throw new ForbiddenOperationException("Operation is forbidden");
    }

    @Override
    public Boolean deleteAll() {
        throw new ForbiddenOperationException("Operation is forbidden");
    }
}
