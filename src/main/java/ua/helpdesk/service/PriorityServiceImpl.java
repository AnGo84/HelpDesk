package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.entity.TicketPriority;

import java.util.List;

//@org.springframework.stereotype.Service("priorityService")
@Transactional
public class PriorityServiceImpl implements TableDataService<TicketPriority> {
    static final Logger logger = LoggerFactory.getLogger(PriorityServiceImpl.class);
    @Autowired
    private TableDateDao<TicketPriority> dao;

    @Override
    public TicketPriority findById(Long id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public TicketPriority findByName(String name) {
        logger.info("FindByName: {}", name);
        TicketPriority ticketPriority = dao.findByName(name);
        return ticketPriority;
    }

    @Override
    public void saveData(TicketPriority ticketPriority) {
        logger.info("Save: {}", ticketPriority);
        dao.save(ticketPriority);
    }

    @Override
    public void updateData(TicketPriority ticketPriority) {
        logger.info("Update: {}", ticketPriority);

        TicketPriority entity = dao.findById(ticketPriority.getId());
        if (entity != null) {
            entity.setId(ticketPriority.getId());
            entity.setName(ticketPriority.getName());
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
    public List<TicketPriority> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Long id, String name) {
        TicketPriority ticketPriority = findByName(name);
        return (ticketPriority == null || ((id != null) && (ticketPriority.getId().equals(id) && ticketPriority.getName().equals(name))));
    }

    @Override
    public boolean isDataUnique(Long id, String name, Long type_id) {
        return false;
    }
}
