package ua.helpdesk.service;

import ua.helpdesk.entity.User;

import java.util.List;


public interface UserService {

    User findById(Long id);

    User findByLogin(String login);

    void saveUser(User user);

    void updateUser(User user);

    void deleteUserByLogin(String login);

    List<User> findAllUsers();

    boolean isUserLoginUnique(Long id, String login);

}