package ua.helpdesk.service;

import java.util.List;

import ua.helpdesk.model.User;


public interface UserService {
	
	User findById(Integer id);
	
	User findByLogin(String login);
	
	void saveUser(User user);
	
	void updateUser(User user);
	
	void deleteUserByLogin(String login);

	List<User> findAllUsers(); 
	
	boolean isUserLoginUnique(Integer id, String login);

}