package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TicketViewDao;
import ua.helpdesk.entities.TicketView;

import java.util.List;

//@Service("ticketViewService")
@Transactional
public class TicketViewServiceImpl implements TicketViewService {
    static final Logger logger = LoggerFactory.getLogger(TicketViewServiceImpl.class);
    @Autowired
    private TicketViewDao dao;

    @Override
    public TicketView findById(Integer id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public TicketView findByName(String name) {
        return null;
    }

    @Override
    public void saveData(TicketView ticket) {
        logger.info("Save: {}", ticket);
        /*dao.save(ticket);

        Ticket entity = dao.findById(ticket.getId());
        if (entity != null) {
            entity.setNumber(ticket.getService().getName().replaceAll(" ", "") + ticket.getId());
        }*/

    }

    @Override
    public void updateData(TicketView ticket) {
        logger.info("Update: {}", ticket);
/*
        Ticket entity = dao.findById(ticket.getId());
        if (entity != null) {
            entity.setId(ticket.getId());
            entity.setNumber(ticket.getNumber());
            entity.setTheme(ticket.getTheme());
            entity.setDescription(ticket.getDescription());
            entity.setService(ticket.getService());
            entity.setCategory(ticket.getCategory());
            entity.setPriority(ticket.getPriority());
            entity.setTicketState(ticket.getTicketState());
            entity.setTicketType(ticket.getTicketType());
            entity.setDate(ticket.getDate());
            entity.setUser(ticket.getUser());
            entity.setPerformer(ticket.getPerformer());
            entity.setSolution(ticket.getSolution());
        }*/
    }

    @Override
    public void deleteDataByName(String name) {
        /*dao.deleteByName(name);*/
    }

    @Override
    public void deleteDataById(Integer id) {
        /*dao.deleteById(id);*/
    }

    @Override
    public List<TicketView> findAllData() {
        return dao.findAllData();
    }

    @Override
    public List<TicketView> findTicketsForUser(String userName) {
        return dao.findTicketsForUser(userName);
    }

    @Override
    public List<TicketView> findTicketsForUser(Integer userID) {
        return dao.findTicketsForUser(userID);
    }
}
