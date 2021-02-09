package ua.helpdesk.service;

import ua.helpdesk.entity.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Long id);

    Category findByName(String name);

    void saveData(Category t);

    void updateData(Category t);

    void deleteDataByName(String name);

    void deleteDataById(Long id);

    List<Category> findAllData();

    boolean isDataUnique(Long id, String name);

    boolean isDataUnique(Long id, String name, Long type_id);

    List<Category> findCategoryForService(String serviceName);

    List<Category> findCategoryForService(Long serviceID);

}
