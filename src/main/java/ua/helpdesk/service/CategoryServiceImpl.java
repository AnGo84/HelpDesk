package ua.helpdesk.service;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.CategoryDao;
import ua.helpdesk.dao.CategoryDaoImpl;
import ua.helpdesk.dao.TableDateDao;
import ua.helpdesk.model.Category;

import java.util.List;

@Service("categoryService")
@Transactional
//public class CategoryServiceImpl implements TableDataService<Category> {
public class CategoryServiceImpl implements CategoryService {
    static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    //private TableDateDao<Category> dao;
    private CategoryDao dao;

    @Override
    public Category findById(Integer id) {
        if (id == null) {
            return null;
        }
        return dao.findById(id);
    }

    @Override
    public Category findByName(String name) {
        return dao.findByName(name);
    }

    @Override
    public void saveData(Category category) {
        logger.info("Save: {}", category);
        dao.save(category);
    }

    @Override
    public void updateData(Category category) {
        Category entity = dao.findById(category.getId());
        if (entity != null) {
            entity.setId(category.getId());
            entity.setName(category.getName());
            entity.setService(category.getService());
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
    public List<Category> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Integer id, String name) {
        return false;
    }

    @Override
    public boolean isDataUnique(Integer id, String name, Integer serviceId) {
        logger.info("Get id: " + id + ", name: " + name + ", serviceId: " + serviceId);
        Category category = findById(id);
        return (category == null || ((id != null) && (category.getId() == id && category.getName().equals(name) && category.getService().getId() == serviceId)));
    }

    @Override
    public List<Category> findCategoryForService(String serviceName){
        return dao.findCategoryForService(serviceName);
    }

    @Override
    public List<Category> findCategoryForService(Integer serviceID){
        return dao.findCategoryForService(serviceID);
    }

}
