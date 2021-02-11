package ua.helpdesk.service;

import ua.helpdesk.entity.AbstractEntity;

import java.util.List;

public interface CommonService<T extends AbstractEntity> {
	T save(T entity);

	T update(T entity);

	T get(Long id);

	List<T> getAll();

	Boolean deleteById(Long id);

    Boolean deleteAll();

    Boolean isExist(T entity);
}
