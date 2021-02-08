package ua.helpdesk.dao;

import java.util.List;


public interface UserTypeDao {

	List<UserType> findAll();
	
	UserType findByName(String name);
	
	UserType findById(Integer id);
}
