package ua.helpdesk.dao;

import ua.helpdesk.entity.Category;

import java.util.List;

@Deprecated
public interface CategoryDao {
    Category findById(Long id);

    Category findByName(String name);

    void save(Category t);

    void deleteByName(String name);

    void deleteById(Long id);

    List<Category> findAllData();

    List<Category> findCategoryForService(String serviceName);

    List<Category> findCategoryForService(Long serviceID);
}
