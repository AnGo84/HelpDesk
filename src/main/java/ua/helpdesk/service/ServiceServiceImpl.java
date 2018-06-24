package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.model.Service;

import java.util.List;

@org.springframework.stereotype.Service("serviceService")
@Transactional
public class ServiceServiceImpl implements TableDataService<Service> {
    static final Logger logger = LoggerFactory.getLogger(ServiceServiceImpl.class);
    @Autowired
    private TableDateDao<Service> dao;

    @Override
    public Service findById(Integer id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public Service findByName(String name) {
        logger.info("FindByName: {}", name);
        Service service = dao.findByName(name);
        return service;
    }

    @Override
    public void saveData(Service service) {
        logger.info("Save: {}", service);
        dao.save(service);
    }

    @Override
    public void updateData(Service service) {
        logger.info("Update: {}", service);

        Service entity = dao.findById(service.getId());
        if (entity != null) {
            entity.setId(service.getId());
            entity.setName(service.getName());
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
    public List<Service> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Integer id, String name) {
        Service service = findByName(name);
        return (service == null || ((id != null) && (service.getId() == id && service.getName().equals(name))));
    }

    @Override
    public boolean isDataUnique(Integer id, String name, Integer type_id) {
        return false;
    }
}
