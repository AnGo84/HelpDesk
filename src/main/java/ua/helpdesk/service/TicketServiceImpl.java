package ua.helpdesk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.entity.Ticket;
import ua.helpdesk.entity.TicketState;
import ua.helpdesk.exception.ForbiddenOperationException;
import ua.helpdesk.repository.TicketRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service
@Transactional
@Slf4j
public class TicketServiceImpl extends AbstractService<Ticket, TicketRepository> {
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss:SS");

    public TicketServiceImpl(TicketRepository repository) {
        super(repository);
    }

    public Ticket findByNumber(String name) {
        log.debug("Find by number: {}", name);
        return repository.findByNumber(name);
    }

    public List<Ticket> getAllSortedByIdDESC() {
        return repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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

    public Ticket addNew(Ticket entity) {
        log.info("Save new entity: {}", entity);
        Date ticketDate = new Date();
        entity.setDate(ticketDate);

        entity.setNumber(DATE_FORMAT.format(ticketDate));

        entity.setSolution("solution");
        Ticket savedEntity = repository.save(entity);
        return savedEntity;

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
