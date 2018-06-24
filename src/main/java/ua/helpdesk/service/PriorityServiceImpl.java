package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.model.Priority;

import java.util.List;

@org.springframework.stereotype.Service("priorityService")
@Transactional
public class PriorityServiceImpl implements TableDataService<Priority> {
    static final Logger logger = LoggerFactory.getLogger(PriorityServiceImpl.class);
    @Autowired
    private TableDateDao<Priority> dao;

    @Override
    public Priority findById(Integer id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public Priority findByName(String name) {
        logger.info("FindByName: {}", name);
        Priority priority = dao.findByName(name);
        return priority;
    }

    @Override
    public void saveData(Priority priority) {
        logger.info("Save: {}", priority);
        dao.save(priority);
    }

    @Override
    public void updateData(Priority priority) {
        logger.info("Update: {}", priority);

        Priority entity = dao.findById(priority.getId());
        if (entity != null) {
            entity.setId(priority.getId());
            entity.setName(priority.getName());
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
    public List<Priority> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Integer id, String name) {
        Priority priority = findByName(name);
        return (priority == null || ((id != null) && (priority.getId() == id && priority.getName().equals(name))));
    }

    @Override
    public boolean isDataUnique(Integer id, String name, Integer type_id) {
        return false;
    }
}
