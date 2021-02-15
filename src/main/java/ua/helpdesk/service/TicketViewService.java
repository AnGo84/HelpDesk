package ua.helpdesk.service;

import ua.helpdesk.entity.TicketView;

import java.util.List;

@Deprecated
public interface TicketViewService {

    TicketView findById(Long id);

    TicketView findByName(String name);

    void saveData(TicketView t);

    void updateData(TicketView t);

    void deleteDataByName(String name);

    void deleteDataById(Long id);

    List<TicketView> findAllData();

    List<TicketView> findTicketsForUser(String userName);

    List<TicketView> findTicketsForUser(Long userID);

}
