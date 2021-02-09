package ua.helpdesk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ua.helpdesk.dao.CategoryDao;
import ua.helpdesk.entity.Category;

import java.util.List;

//@Service("categoryService")
@Transactional
//public class CategoryServiceImpl implements TableDataService<Category> {
public class CategoryServiceImpl implements CategoryService {
    static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    //private TableDateDao<Category> dao;
    private CategoryDao dao;

    @Override
    public Category findById(Long id) {
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
    public void deleteDataById(Long id) {
        dao.deleteById(id);
    }

    @Override
    public List<Category> findAllData() {
        return dao.findAllData();
    }

    @Override
    public boolean isDataUnique(Long id, String name) {
        return false;
    }

    @Override
    public boolean isDataUnique(Long id, String name, Long serviceId) {
        logger.info("Get id: " + id + ", name: " + name + ", serviceId: " + serviceId);
        Category category = findById(id);
        return (category == null || ((id != null) && (category.getId() == id && category.getName().equals(name) && category.getService().getId() == serviceId)));
    }

    @Override
    public List<Category> findCategoryForService(String serviceName){
        return dao.findCategoryForService(serviceName);
    }

    @Override
    public List<Category> findCategoryForService(Long serviceID) {
        return dao.findCategoryForService(serviceID);
    }

}
