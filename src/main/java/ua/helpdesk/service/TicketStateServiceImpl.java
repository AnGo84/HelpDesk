package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.entities.TicketState;

import java.util.List;

@org.springframework.stereotype.Service("ticketStateService")
@Transactional
public class TicketStateServiceImpl implements TableDataService<TicketState> {
    static final Logger logger = LoggerFactory.getLogger(TicketStateServiceImpl.class);
    @Autowired
    private TableDateDao<TicketState> dao;

    @Override
    public TicketState findById(Integer id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public TicketState findByName(String name) {
        logger.info("FindByName: {}", name);
        TicketState ticketState = dao.findByName(name);
        return ticketState;
    }

    @Override
    public void saveData(TicketState ticketState) {
        logger.info("Save: {}", ticketState);
        dao.save(ticketState);
    }

    @Override
    public void updateData(TicketState ticketState) {
        logger.info("Update: {}", ticketState);

        TicketState entity = dao.findById(ticketState.getId());
        if (entity != null) {
            entity.setId(ticketState.getId());
            entity.setName(ticketState.getName());
            entity.setAltName(ticketState.getAltName());
        }
    }

    @Override
    public void deleteDataByName(String name) {
        dao.deleteByName(name);
    }

    @Override
    public void deleteDataById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public List<TicketState> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Integer id, String name) {
        TicketState ticketState = findByName(name);
        return (ticketState == null || ((id != null) && (ticketState.getId() == id && ticketState.getName().equals(name))));
    }

    @Override
    public boolean isDataUnique(Integer id, String name, Integer type_id) {
        return false;
    }
}
