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

    public static Service getService(Long id, String name) {
        Service service = new Service();
        service.setId(id);
        service.setName(name);
        return service;
    }

    public static Category getCategory(Long id, String name, Service service) {
        Category category = new Category();
        category.setId(id);
        category.setName(name);
        category.setService(service);
        return category;
    }

    public static Ticket getTicket(Long id, String number, String description, String theme, Category category, TicketPriority ticketPriority, TicketState ticketState, TicketType ticketType, User user, User performer, String solution) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setNumber(number);
        ticket.setDescription(description);
        ticket.setTheme(theme);
        ticket.setService(category.getService());
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
}
