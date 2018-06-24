package ua.helpdesk.service;

import ua.helpdesk.model.TicketView;

import java.util.List;

public interface TicketViewService {

    TicketView findById(Integer id);

    TicketView findByName(String name);

    void saveData(TicketView t);

    void updateData(TicketView t);

    void deleteDataByName(String name);

    void deleteDataById(Integer id);

    List<TicketView> findAllData();

    List<TicketView> findTicketsForUser(String userName);

    List<TicketView> findTicketsForUser(Integer userID);

}
