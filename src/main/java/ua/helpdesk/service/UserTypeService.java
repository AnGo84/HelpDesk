package ua.helpdesk.service;

import java.util.List;

import ua.helpdesk.model.UserType;


public interface UserTypeService {

	UserType findById(Integer id);

	UserType findByName(String name);
	
	List<UserType> findAll();
	
}
