package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.entity.Group;

import java.util.List;

//@Service("groupService")
@Transactional
public class GroupServiceImpl implements TableDataService<Group> {
    static final Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);
    @Autowired
    private TableDateDao<Group> dao;

    @Override
    //@Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
    public Group findById(Long id) {
        logger.info("FindById: {}", id);
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public Group findByName(String name) {
        logger.info("FindByName: {}", name);
        Group group = dao.findByName(name);
        return group;
    }

    @Override
    public void saveData(Group group) {
        logger.info("Save: {}", group);
        dao.save(group);
    }

    @Override
    public void updateData(Group group) {
        logger.info("Update: {}", group);

        Group entity = dao.findById(group.getId());
        if (entity != null) {
            entity.setId(group.getId());
            entity.setName(group.getName());
            entity.setCategories(group.getCategories());
            entity.setRight(group.getRight());
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
    public List<Group> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Long id, String name) {
        Group group = findByName(name);
        return (group == null || ((id != null) && (group.getId() == id && group.getName().equals(name))));
    }

    @Override
    public boolean isDataUnique(Long id, String name, Long type_id) {
        return false;
    }
}
