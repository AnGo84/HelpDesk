package ua.helpdesk.dao;

import java.util.List;

import ua.helpdesk.model.UserType;


public interface UserTypeDao {

	List<UserType> findAll();
	
	UserType findByName(String name);
	
	UserType findById(Integer id);
}
