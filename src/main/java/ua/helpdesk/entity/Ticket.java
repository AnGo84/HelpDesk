package ua.helpdesk.entity;

import lombok.Data;
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
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @JoinColumn(name = "SERVICE_ID", nullable = false)
    private Service service;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRIORITY_ID", nullable = false)
    private TicketPriority ticketPriority;

    @Column(name = "TICKET_STATE_ID")
    @Convert(converter = TicketStateConverter.class)
    private TicketState ticketState;

    @NotNull
    @Column(name = "TICKET_TYPE_ID")
    @Convert(converter = TicketTypeConverter.class)
    private TicketType ticketType;

    @NotNull
    @Column(name = "DATE", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PERFORMER_ID")
    private User performer;

    @Column(name = "SOLUTION")
    @Size(max = 1000)
    private String solution;

    /*@OneToMany( orphanRemoval = true)
    @JoinColumn(name = "ticket_id")
    private List<TicketMessage> messages;*/

}
