package ua.helpdesk.dao;

import ua.helpdesk.entity.TicketView;

import java.util.List;
@Deprecated
public interface TicketViewDao {
    TicketView findById(Long id);

    TicketView findByName(String name);

    void save(TicketView t);

    void deleteByName(String name);

    void deleteById(Long id);

    List<TicketView> findAllData();

    List<TicketView> findTicketsForUser(String userName);

    List<TicketView> findTicketsForUser(Long userID);
}
