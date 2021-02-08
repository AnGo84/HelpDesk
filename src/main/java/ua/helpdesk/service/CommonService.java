package ua.helpdesk.service;

import java.util.List;

public interface CommonService<T> {
    T save(T entity);

    List<T> saveAll(List<T> entities);

    T update(T entity);

    T get(Long id);

    List<T> getAll();

    Boolean deleteById(Long id);

    Boolean deleteAll();

    Boolean isExist(T entity);
}
