package ua.helpdesk.service;

import ua.helpdesk.entities.User;

import java.util.List;


public interface UserService {
	
	User findById(Integer id);
	
	User findByLogin(String login);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByLogin(String login);

	List<User> findAllUsers(); 
	
	boolean isUserLoginUnique(Integer id, String login);

}