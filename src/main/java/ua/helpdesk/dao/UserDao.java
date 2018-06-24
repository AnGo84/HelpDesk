package ua.helpdesk.dao;

import java.util.List;

import ua.helpdesk.model.User;


public interface UserDao {

    User findById(Integer id);

    User findByLogin(String login);

    void save(User user);

    void deleteByLogin(String login);

    List<User> findAllUsers();

}

