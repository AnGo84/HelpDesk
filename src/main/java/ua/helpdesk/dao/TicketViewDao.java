package ua.helpdesk.dao;

import ua.helpdesk.entities.TicketView;

import java.util.List;

public interface TicketViewDao {
    TicketView findById(Integer id);

    TicketView findByName(String name);

    void save(TicketView t);

    void deleteByName(String name);

    void deleteById(Integer id);

    List<TicketView> findAllData();

    List<TicketView> findTicketsForUser(String userName);

    List<TicketView> findTicketsForUser(Integer userID);
}
