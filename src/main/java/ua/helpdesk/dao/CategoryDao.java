package ua.helpdesk.dao;

import ua.helpdesk.model.Category;
import ua.helpdesk.service.TableDataService;

import java.util.List;

public interface CategoryDao {
    Category findById(Integer id);

    Category findByName(String name);

    void save(Category t);

    void deleteByName(String name);

    void deleteById(Integer id);

    List<Category> findAllData();

    List<Category> findCategoryForService(String serviceName);

    List<Category> findCategoryForService(Integer serviceID);
}
