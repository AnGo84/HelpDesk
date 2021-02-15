package ua.helpdesk.dao;

import java.util.List;
@Deprecated
public interface TableDateDao<T> {
    T findById(Long id);

    T findByName(String name);

    void save(T t);

    void deleteByName(String name);

    void deleteById(Long id);

    List<T> findAllData();
}
