package ua.helpdesk.dao;

import ua.helpdesk.entities.User;

import java.util.List;


public interface UserDao {

    User findById(Integer id);

    User findByLogin(String login);

    void save(User user);

    void deleteByLogin(String login);

    List<User> findAllUsers();

}

