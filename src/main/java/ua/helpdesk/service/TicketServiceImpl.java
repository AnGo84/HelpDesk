package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.entity.Ticket;

import java.util.List;

//@org.springframework.stereotype.Service("ticketService")
@Transactional
public class TicketServiceImpl implements TableDataService<Ticket> {
    static final Logger logger = LoggerFactory.getLogger(TicketServiceImpl.class);
    @Autowired
    private TableDateDao<Ticket> dao;

    @Override
    public Ticket findById(Long id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public Ticket findByName(String name) {
        logger.info("FindByName: {}", name);
        Ticket ticket = dao.findByName(name);
        return ticket;
    }

    @Override
    public void saveData(Ticket ticket) {
        logger.info("Save: {}", ticket);
        dao.save(ticket);

        Ticket entity = dao.findById(ticket.getId());
        if (entity != null) {
            entity.setNumber(ticket.getService().getName().replaceAll(" ", "") + ticket.getId());
        }

    }

    @Override
    public void updateData(Ticket ticket) {
        logger.info("Update: {}", ticket);

        Ticket entity = dao.findById(ticket.getId());
        if (entity != null) {
            entity.setId(ticket.getId());
            entity.setNumber(ticket.getNumber());
            entity.setTheme(ticket.getTheme());
            entity.setDescription(ticket.getDescription());
            entity.setService(ticket.getService());
            entity.setCategory(ticket.getCategory());
            entity.setTicketPriority(ticket.getTicketPriority());
            entity.setTicketState(ticket.getTicketState());
            entity.setTicketType(ticket.getTicketType());
            entity.setDate(ticket.getDate());
            entity.setUser(ticket.getUser());
            entity.setPerformer(ticket.getPerformer());
            entity.setSolution(ticket.getSolution());
        }
    }

    @Override
    public void deleteDataByName(String name) {
        dao.deleteByName(name);
    }

    @Override
    public void deleteDataById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<Ticket> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Long id, String name) {
        Ticket ticket = findByName(name);
        return (ticket == null || ((id != null) && (ticket.getId() == id && ticket.getNumber().equals(name))));
    }

    @Override
    public boolean isDataUnique(Long id, String name, Long type_id) {
        return false;
    }

    public List<Ticket> findAllDataForUser() {

        return dao.findAllData();
    }
}
