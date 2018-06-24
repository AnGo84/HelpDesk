package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.model.Service;
import ua.helpdesk.model.TicketType;

import java.util.List;

@org.springframework.stereotype.Service("ticketTypeService")
@Transactional
public class TicketTypeServiceImpl implements TableDataService<TicketType> {
    static final Logger logger = LoggerFactory.getLogger(TicketTypeServiceImpl.class);
    @Autowired
    private TableDateDao<TicketType> dao;

    @Override
    public TicketType findById(Integer id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public TicketType findByName(String name) {
        logger.info("FindByName: {}", name);
        TicketType ticketType = dao.findByName(name);
        return ticketType;
    }

    @Override
    public void saveData(TicketType ticketType) {
        logger.info("Save: {}", ticketType);
        dao.save(ticketType);
    }

    @Override
    public void updateData(TicketType ticketType) {
        logger.info("Update: {}", ticketType);

        TicketType entity = dao.findById(ticketType.getId());
        if (entity != null) {
            entity.setId(ticketType.getId());
            entity.setName(ticketType.getName());
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
    public List<TicketType> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Integer id, String name) {
        TicketType ticketType = findByName(name);
        return (ticketType == null || ((id != null) && (ticketType.getId() == id && ticketType.getName().equals(name))));
    }

    @Override
    public boolean isDataUnique(Integer id, String name, Integer type_id) {
        return false;
    }
}
