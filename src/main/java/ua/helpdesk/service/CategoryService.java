package ua.helpdesk.service;

import ua.helpdesk.entities.Category;

import java.util.List;

public interface CategoryService {

    Category findById(Integer id);

    Category findByName(String name);

    void saveData(Category t);

    void updateData(Category t);

    void deleteDataByName(String name);

    void deleteDataById(Integer id);

    List<Category> findAllData();

    boolean isDataUnique(Integer id, String name);

    boolean isDataUnique(Integer id, String name, Integer type_id);

    List<Category> findCategoryForService(String serviceName);

    List<Category> findCategoryForService(Integer serviceID);

}
