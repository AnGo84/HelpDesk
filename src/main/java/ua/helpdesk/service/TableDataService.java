package ua.helpdesk.service;

import java.util.List;

public interface TableDataService<T> {

    T findById(Integer id);

    T findByName(String name);

    void saveData(T t);

    void updateData(T t);

    void deleteDataByName(String name);

    void deleteDataById(Integer id);

    List<T> findAllData();

    boolean isDataUnique(Integer id, String name);

    boolean isDataUnique(Integer id, String name, Integer type_id);
}
