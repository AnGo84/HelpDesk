package ua.helpdesk;

import ua.helpdesk.entity.*;

import java.util.Date;

public class TestDataUtils {
    public static User getUser(Long id, String login, String pass, boolean isActive, UserType userType) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setLastName(login + id);
        user.setFirstName(login + id);
        user.setMiddleName(login + id);
        user.setPassword(pass);
        user.setActive(isActive);
        user.setUserType(userType);
        return user;
    }

    public static TicketPriority getTicketPriority(Long id, String name, Integer timeLimit) {
        TicketPriority ticketPriority = new TicketPriority();
        ticketPriority.setId(id);
        ticketPriority.setName(name);
        ticketPriority.setTimeLimit(timeLimit);
        return ticketPriority;
    }

    public static AppService getAppService(Long id, String name) {
        AppService service = new AppService();
        service.setId(id);
        service.setName(name);
        return service;
    }

    public static Category getCategory(Long id, String name, AppService service) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setAppService(service);
        return category;
    }

    public static Ticket getTicket(Long id, String number, String description, String theme, Category category, TicketPriority ticketPriority, TicketState ticketState, TicketType ticketType, User user, User performer, String solution) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setNumber(number);
        ticket.setDescription(description);
        ticket.setTheme(theme);
        ticket.setService(category.getAppService());
        ticket.setCategory(category);
        ticket.setTicketPriority(ticketPriority);
        ticket.setTicketState(ticketState);
        ticket.setTicketType(ticketType);
        ticket.setDate(new Date());
        ticket.setUser(user);
        ticket.setPerformer(performer);
        ticket.setSolution(solution);

        return ticket;
    }

    public static TicketMessage getTicketMessage(Long id, Ticket ticket, User user, Date date, String text) {
        TicketMessage ticketMessage = new TicketMessage();
        ticketMessage.setId(id);
        ticketMessage.setTicket(ticket);
        ticketMessage.setUser(user);
        ticketMessage.setDate(date);
        ticketMessage.setText(text);
        return ticketMessage;
    }

    public static String getMockStringWithLength(int length) {
        StringBuilder result = new StringBuilder();
        while ((length--) >= 0) {
            result.append("a");
        }
        return result.toString();
    }
}
