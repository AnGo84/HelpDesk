package ua.helpdesk.dao;

import ua.helpdesk.entity.User;

import java.util.List;


public interface UserDao {

    User findById(Long id);

    User findByLogin(String login);

    void save(User user);

    void deleteByLogin(String login);

    List<User> findAllUsers();

}

