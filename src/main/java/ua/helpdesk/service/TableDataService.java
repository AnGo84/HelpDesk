package ua.helpdesk.service;

import java.util.List;

public interface TableDataService<T> {

    T findById(Long id);

    T findByName(String name);

    void saveData(T t);

    void updateData(T t);

    void deleteDataByName(String name);

    void deleteDataById(Long id);

    List<T> findAllData();

    boolean isDataUnique(Long id, String name);

    boolean isDataUnique(Long id, String name, Long type_id);
}
