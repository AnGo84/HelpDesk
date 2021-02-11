package ua.helpdesk.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import ua.helpdesk.entity.converter.TicketStateConverter;
import ua.helpdesk.entity.converter.TicketTypeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "TICKETS")
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Ticket extends AbstractEntity {

    private String number;

    private String theme;

    private String description;

    private Service service;

    private Category category;

    private TicketPriority ticketPriority;

    private TicketState ticketState;

    private TicketType ticketType;

    private Date date;

    private User user;

    private User performer;

    private String solution;

    /*@OneToMany( orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private List<TicketMessage> messages;*/

    @Column(name = "NUMBER", unique = true, nullable = false)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @NotEmpty
    @Column(name = "THEME", nullable = false)
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @NotEmpty
    @Column(name = "DESCRIPTION", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SERVICE_ID", nullable = false)
    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIORITY_ID", nullable = false)
    public TicketPriority getTicketPriority() {
        return ticketPriority;
    }

    public void setTicketPriority(TicketPriority ticketPriority) {
        this.ticketPriority = ticketPriority;
    }

    @Column(name = "TICKET_STATE_ID")
    @Convert(converter = TicketStateConverter.class)
    public TicketState getTicketState() {
        return ticketState;
    }

    public void setTicketState(TicketState ticketState) {
        this.ticketState = ticketState;
    }

    @NotNull
    @Column(name = "TICKET_TYPE_ID")
    @Convert(converter = TicketTypeConverter.class)
    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    @NotNull
    @Column(name = "DATE", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERFORMER_ID")
    public User getPerformer() {
        return performer;
    }

    public void setPerformer(User performer) {
        this.performer = performer;
    }

    @Column(name = "SOLUTION")
    @Size(max = 1000)
    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
