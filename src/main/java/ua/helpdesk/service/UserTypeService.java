package ua.helpdesk.service;

import ua.helpdesk.entities.UserType;

import java.util.List;


public interface UserTypeService {

	UserType findById(Integer id);

	UserType findByName(String name);
	
	List<UserType> findAll();
	
}
