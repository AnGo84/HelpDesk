package ua.helpdesk.model;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TICKETS")

public class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @NotEmpty
    @Column(name = "NUMBER", unique = true, nullable = false)
    private String number;

    @NotEmpty
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @NotEmpty
    @Column(name = "THEME", nullable = false)
    private String theme;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_ID")
    private Service service;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID")
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIORITY_ID")
    private Priority priority;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TICKET_STATE_ID")
    private TicketState ticketState;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TICKET_TYPE_ID")
    private TicketType ticketType;


    @Column(name = "DATE", nullable = true)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERFORMER_ID")
    private User performer;

    @Column(name = "SOLUTION", nullable = false)
    private String solution;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TicketState getTicketState() {
        return ticketState;
    }

    public void setTicketState(TicketState ticketState) {
        this.ticketState = ticketState;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getPerformer() {
        return performer;
    }

    public void setPerformer(User performer) {
        this.performer = performer;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;

        Ticket ticket = (Ticket) o;

        if (!id.equals(ticket.id)) return false;
        if (!number.equals(ticket.number)) return false;
        if (!theme.equals(ticket.theme)) return false;
        if (!description.equals(ticket.description)) return false;
        if (!service.equals(ticket.service)) return false;
        if (!category.equals(ticket.category)) return false;
        if (!priority.equals(ticket.priority)) return false;
        if (!ticketState.equals(ticket.ticketState)) return false;
        if (!ticketType.equals(ticket.ticketType)) return false;
        if (!date.equals(ticket.date)) return false;
        if (!user.equals(ticket.user)) return false;
        if (!performer.equals(ticket.performer)) return false;
        return solution.equals(ticket.solution);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((number == null) ? 0 : number.hashCode());
        result = prime * result + ((theme == null) ? 0 : theme.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((service == null) ? 0 : service.hashCode());
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((priority == null) ? 0 : priority.hashCode());
        result = prime * result + ((ticketState == null) ? 0 : ticketState.hashCode());
        result = prime * result + ((ticketType == null) ? 0 : ticketType.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((performer == null) ? 0 : performer.hashCode());
        result = prime * result + ((solution == null) ? 0 : solution.hashCode());
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ticket{");
        sb.append("id=").append(id);
        sb.append(", number='").append(number).append('\'');
        sb.append(", theme='").append(theme).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", service=").append(service);
        sb.append(", category=").append(category);
        sb.append(", priority=").append(priority);
        sb.append(", ticketState=").append(ticketState);
        sb.append(", ticketType=").append(ticketType);
        sb.append(", date=").append(date);
        sb.append(", user=").append(user);
        sb.append(", performer=").append(performer);
        sb.append(", solution='").append(solution).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
